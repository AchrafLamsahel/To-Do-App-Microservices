package org.usermicroservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ValidationException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.usermicroservice.dto.Task;
import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.dto.UserResponseDto;
import org.usermicroservice.entities.User;
import org.usermicroservice.exceptions.EmailAlreadyExistsException;
import org.usermicroservice.exceptions.EmptyEntityException;
import org.usermicroservice.exceptions.UserNotFoundException;
import org.usermicroservice.feignClient.TaskClient;
import org.usermicroservice.mappers.MappingProfile;
import org.usermicroservice.repositories.UserRepository;
import org.usermicroservice.retrieve.RabbitMqGetUserTasks;
import org.usermicroservice.utils.UserInputValidation;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqGetUserTasks rabbitMqGetUserTasks;
    private final TaskClient taskClient;

    @Override
    public List<UserResponseDto> getAllUsers() throws UserNotFoundException {
        return userRepository.findAll()
                .stream()
                .map(MappingProfile::mapToUserDto)
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) throws UserNotFoundException, EmptyEntityException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id: " + id + " NOT found!"));
//      List<Task> tasks = taskClient.findByUserId(id);
        List<Task> tasks = rabbitMqGetUserTasks.getUserTasks(id, "getUserTasksRoutingKey");
        System.out.println(tasks);
        user.setTasks(tasks);
        return MappingProfile.mapToUserDto(user);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userDto) throws EmailAlreadyExistsException {
        var validationErrors = UserInputValidation.validate(userDto);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }
        var userEntity = MappingProfile.mapToUserEntity(userDto);
        return MappingProfile.mapToUserDto(userRepository.save(userEntity));
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException, EmptyEntityException {
        if (userId == null || userId <= 0) {
            throw new EmptyEntityException("Empty Input ! --> id =  " + userId);
        }
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with id: " + userId + "  NOT found!");
        }
        userRepository.deleteUserById(userId);
        rabbitTemplate.convertAndSend("tasksExchange", "tasksRouting", userId);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) throws UserNotFoundException {
        var validationErrors = UserInputValidation.validate(userDto);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User NOT Found"));
        return MappingProfile.mapToUserDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email)
                .map(MappingProfile::mapToUserDto)
                .orElseThrow(() -> new RuntimeException("User with email is not found."));
    }

    @Override
    public UserResponseDto getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUserName(username)
                .map(MappingProfile::mapToUserDto)
                //  .map(Optional::of)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserResponseDto getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        return userRepository.findByUserNameAndPassword(username, password)
                .map(MappingProfile::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found. Please check your credentials."));
    }

    @Override
    public UserResponseDto getUserByEmailAndPassword(String email, String password) throws UserNotFoundException {
        return userRepository.findByEmailAndPassword(email, password)
                .map(MappingProfile::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found. Please check your credentials."));
    }

    //    @RabbitListener(queues = "isUserIdExistQueue")
//    public ResponseEntity<?> receiveAnswer(Long userId) throws NotFoundException {
//        if (userRepo.existsById(userId)) {
//            return new ResponseEntity<>(true, HttpStatus.OK);
//        }else {
//            rabbitTemplate.convertAndSend("tasksExchange", "tasksRouting", userId);
//            throw new NotFoundException("User with id: "+userId+" NOT found!");
//        }
//
//    }
}
