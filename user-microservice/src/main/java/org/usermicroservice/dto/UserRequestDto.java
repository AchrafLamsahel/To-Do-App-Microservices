package org.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private Long id;
    private String firstName;
    private String lastName;
    private  String userName;
    private String password;
    private String email;
    private List<Task> tasks;
}
