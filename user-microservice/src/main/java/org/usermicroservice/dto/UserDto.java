package org.usermicroservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.usermicroservice.entities.UserDetails;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private UserDetails userDetails;
}
