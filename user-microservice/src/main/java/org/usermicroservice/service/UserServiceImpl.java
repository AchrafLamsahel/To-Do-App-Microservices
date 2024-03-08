package org.usermicroservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.dto.UserResponseDto;
import org.usermicroservice.exceptions.EmailAlreadyExistsException;
import org.usermicroservice.exceptions.EmptyEntityException;
import org.usermicroservice.exceptions.UserNotFoundException;
import org.usermicroservice.repositories.UserRepository;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService{
    private final UserRepository userRepository;
    @Override
    public List<UserResponseDto> getAllUsers() throws UserNotFoundException {

        return null;
    }

    @Override
    public UserResponseDto getUserById(Long id) throws UserNotFoundException, EmptyEntityException {
        return null;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userDto) throws EmailAlreadyExistsException {
        return null;
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException, EmptyEntityException {

    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) throws UserNotFoundException {
        return null;
    }

    @Override
    public UserResponseDto getUserByEmail(String email) throws UserNotFoundException {
        return null;
    }

    @Override
    public UserResponseDto getUserByUsername(String username) throws UserNotFoundException {
        return null;
    }

    @Override
    public UserResponseDto getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        return null;
    }

    @Override
    public UserResponseDto getUserByEmailAndPassword(String email, String password) throws UserNotFoundException {
        return null;
    }
}
