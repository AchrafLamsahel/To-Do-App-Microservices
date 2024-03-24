package org.usermicroservice.dto;

import lombok.Data;
import org.usermicroservice.enumerations.Active;
import org.usermicroservice.enumerations.Role;
import java.util.List;
@Data
public class UserResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private  String username;
    private String password;
    private String email;
    private Role role;
    private Active active;
    private List<Task> tasks;
}
