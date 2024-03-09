package org.tasksmicroservice.web;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tasksmicroservice.dto.TaskRequestDto;
import org.tasksmicroservice.dto.TaskResponseDto;
import org.tasksmicroservice.exceptions.TaskNotFoundException;
import org.tasksmicroservice.service.ITaskService;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final ITaskService iTaskService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskResponseDto> getAllTasks() {
        return iTaskService.getAllTasks();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskResponseDto getTaskById(@PathVariable("id") Long id) throws TaskNotFoundException {
        return iTaskService.getTaskById(id);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskResponseDto createTask(@RequestBody TaskRequestDto taskDto) {
        return iTaskService.createTask(taskDto);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskResponseDto updateById(@PathVariable("id") Long id, @RequestBody TaskRequestDto taskDto) {
        return iTaskService.updateTask(id, taskDto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@PathVariable("id") Long id) throws TaskNotFoundException {
        iTaskService.deleteTask(id);
    }
}
