package org.tasksmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tasksmicroservice.enumerations.Status;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private Status status;
    private Date dueDate;
}
