package org.tasksmicroservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ValidationException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.tasksmicroservice.dto.TaskRequestDto;
import org.tasksmicroservice.dto.TaskResponseDto;
import org.tasksmicroservice.enumerations.MessageError;
import org.tasksmicroservice.exceptions.TaskAlreadyExistsException;
import org.tasksmicroservice.exceptions.TaskNotFoundException;
import org.tasksmicroservice.exceptions.UserNotFoundException;
import org.tasksmicroservice.mappers.MappingProfile;
import org.tasksmicroservice.repositories.TaskRepository;
import org.tasksmicroservice.retrieve.RabbitMqUserExistenceClient;
import org.tasksmicroservice.utils.TaskInputValidation;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements ITaskService {
    private final TaskRepository taskRepository;
    private final RabbitMqUserExistenceClient userExistenceClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public boolean isTaskIdExists(Long taskId) {
        return taskRepository.existsById(taskId);
    }

    @Override
    public List<TaskResponseDto> getAllTasks() throws TaskNotFoundException {
        return taskRepository.findAll()
                .stream()
                .map(MappingProfile::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskDto) throws TaskAlreadyExistsException, TaskNotFoundException {
        var validationErrors = TaskInputValidation.validate(taskDto);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }
        if (!userExistenceClient.checkUserExistence(taskDto.getUserId())) {
            throw new UserNotFoundException(MessageError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage() + taskDto.getUserId());
        }
        var task = MappingProfile.mapToEntity(taskDto);
        return MappingProfile.mapToDto(taskRepository.save(task));
    }

    @Override
    public TaskResponseDto getTaskById(Long id) throws TaskNotFoundException {
        var task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(MessageError.TASK_NOT_FOUND.getMessage()));
        return MappingProfile.mapToDto(task);

    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) throws TaskNotFoundException {
        var task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(
                        MessageError.TASK_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        task.setDescription(taskDto.getDescription());
        task.setId(taskDto.getId());
        task.setStatus(taskDto.getStatus());
        task.setStatus(taskDto.getStatus());
        task.setDueDate(task.getDueDate());
        return MappingProfile.mapToDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) throws TaskNotFoundException {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(
                        MessageError.TASK_NOT_FOUND_WITH_ID_EQUALS.getMessage() + id));
        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponseDto> findByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(MappingProfile::mapToDto)
                .toList();
    }

    @RabbitListener(queues = "tasksQueue")
    public void receiveMessage(Long userId) {
        taskRepository.deleteTasksByUserId(userId);
    }
/*
    private void deleteTasksByUserId(Long userId) {
        //userRepository.deleteById(userId);
        rabbitTemplate.convertAndSend("tasksExchange", "tasksRouting", userId);
    }

 */
}
