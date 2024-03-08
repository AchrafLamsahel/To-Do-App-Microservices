package org.usermicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private List<Task> tasks;
}
