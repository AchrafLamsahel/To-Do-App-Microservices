package org.usermicroservice.service;

import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.dto.UserResponseDto;
import org.usermicroservice.entities.User;
import org.usermicroservice.exceptions.EmailAlreadyExistsException;
import org.usermicroservice.exceptions.EmptyEntityException;
import org.usermicroservice.exceptions.UserNotFoundException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserResponseDto> getAllUsers() throws UserNotFoundException;
    UserResponseDto getUserById(Long id) throws  UserNotFoundException, EmptyEntityException ;
    UserResponseDto createUser(UserRequestDto userDto) throws EmailAlreadyExistsException;
    void deleteUser(Long id) throws  UserNotFoundException, EmptyEntityException;
    UserResponseDto updateUser(Long id, UserRequestDto userDto) throws UserNotFoundException;
    UserResponseDto getUserByEmail(String email) throws UserNotFoundException;
    UserResponseDto getUserByUsername(String username) throws UserNotFoundException;
    UserResponseDto getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException;
    UserResponseDto getUserByEmailAndPassword(String email, String password) throws UserNotFoundException;
}
