package org.usermicroservice.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.usermicroservice.dto.UserDto;
import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.dto.UserResponseDto;
import org.usermicroservice.service.IUserService;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final IUserService iUserService;
    @GetMapping("/")
    //@PreAuthorize("hasRole('ADMIN')")
    public Collection<UserResponseDto> getAllUsers() {
        return iUserService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return iUserService.getUserById(id);
    }
    @PostMapping("/register")
    public UserDto createUser(@RequestBody UserRequestDto user) {
        return iUserService.createUser(user);
    }

    @GetMapping("/username={username}")
    public UserResponseDto getUserByUsername(@PathVariable String username) {
        return iUserService.getUserByUsername(username);
    }
    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(iUserService.getUserByEmail(email));
    }
    @GetMapping("/email={email}")
    public UserResponseDto findByEmail(@PathVariable String email) {
        return iUserService.getUserByEmail(email);
    }
    @DeleteMapping("/{id}")
    public UserResponseDto deleteUserBYID(@PathVariable Long id) {
        return iUserService.getUserById(id);
    }
}
