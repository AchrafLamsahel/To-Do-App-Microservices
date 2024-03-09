package org.tasksmicroservice.mappers;

import org.modelmapper.ModelMapper;
import org.tasksmicroservice.dto.TaskRequestDto;
import org.tasksmicroservice.dto.TaskResponseDto;
import org.tasksmicroservice.entities.Task;

public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static Task mapToEntity(TaskRequestDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }
    public static TaskResponseDto mapToDto(Task task) {
        return modelMapper.map(task, TaskResponseDto.class);
    }
}
