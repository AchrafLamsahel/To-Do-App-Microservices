package org.usermicroservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.usermicroservice.dto.Task;
import org.usermicroservice.enumerations.Active;
import org.usermicroservice.enumerations.Role;

import java.util.List;

@Entity(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {
    @Column(unique = true, nullable = false, updatable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Active active;
    @Embedded
    private UserDetails userDetails;
    @Transient
    private List<Task> tasks;
}
