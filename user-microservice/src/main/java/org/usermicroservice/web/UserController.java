package org.usermicroservice.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public Collection<UserResponseDto> getAllUsers() {
        return iUserService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return iUserService.getUserById(id);
    }
    @PostMapping("/")
    public UserResponseDto createUser(@RequestBody UserRequestDto user) {
        return iUserService.createUser(user);
    }

    @GetMapping("/username={username}")
    public UserResponseDto getUserByUsername(@PathVariable String username) {
        return iUserService.getUserByUsername(username);
    }
    @GetMapping("/email={email}")
    public UserResponseDto getUserByEmail(@PathVariable String email) {
        return iUserService.getUserByEmail(email);
    }
    @DeleteMapping("/{id}")
    public UserResponseDto deleteUserBYID(@PathVariable Long id) {
        return iUserService.getUserById(id);
    }
}
