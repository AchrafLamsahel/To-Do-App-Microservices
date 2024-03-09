package org.tasksmicroservice.service;

import org.tasksmicroservice.dto.TaskRequestDto;
import org.tasksmicroservice.dto.TaskResponseDto;
import org.tasksmicroservice.exceptions.TaskAlreadyExistsException;
import org.tasksmicroservice.exceptions.TaskNotFoundException;
import java.util.List;

public interface ITaskService {
    public boolean isTaskIdExists(Long taskId);
    List<TaskResponseDto> getAllTasks() throws TaskNotFoundException;
    TaskResponseDto createTask(TaskRequestDto taskDto) throws TaskAlreadyExistsException, TaskNotFoundException;
    TaskResponseDto getTaskById(Long id) throws TaskNotFoundException;
    TaskResponseDto updateTask(Long id, TaskRequestDto taskDto) throws TaskNotFoundException;
    void deleteTask(Long id) throws TaskNotFoundException;
    List<TaskResponseDto> findByUserId(Long userId);
}
