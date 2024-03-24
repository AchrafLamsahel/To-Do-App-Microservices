package org.securitymicroservice.dto;

import lombok.Data;
import org.securitymicroservice.enums.Role;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}
