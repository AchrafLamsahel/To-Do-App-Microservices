package org.usermicroservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ValidationException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.usermicroservice.dto.Task;
import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.dto.UserResponseDto;
import org.usermicroservice.entities.User;
import org.usermicroservice.enumerations.MessagesError;
import org.usermicroservice.exceptions.EmailAlreadyExistsException;
import org.usermicroservice.exceptions.EmptyEntityException;
import org.usermicroservice.exceptions.UserNotFoundException;
import org.usermicroservice.feignClient.TaskClient;
import org.usermicroservice.mappers.MappingProfile;
import org.usermicroservice.repositories.UserRepository;
import org.usermicroservice.retrieve.RabbitMqGetUserTasks;
import org.usermicroservice.utils.UserInputValidation;

import java.util.List;

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
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(MappingProfile::mapToUserDto)
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) throws UserNotFoundException, EmptyEntityException {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        MessagesError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage()+id));
//      List<Task> tasks = taskClient.findByUserId(id);
        List<Task> tasks = rabbitMqGetUserTasks.getUserTasks(id, "getUserTasksRoutingKey");
        user.setTasks(tasks);
        return MappingProfile.mapToUserDto(user);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) throws EmailAlreadyExistsException {
        log.info("Creating new user: {}", userRequestDto.getEmail());
        var validationErrors = UserInputValidation.validate(userRequestDto);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }
        userRequestDto.setPassword(BCrypt.hashpw(userRequestDto.getPassword(), BCrypt.gensalt()));
        var userEntity = MappingProfile.mapToUserEntity(userRequestDto);
        return MappingProfile.mapToUserDto(userRepository.save(userEntity));
    }
    //TODO edit exception
    @Override
    public void deleteUser(Long userId) throws UserNotFoundException, EmptyEntityException {
        log.info("delete user by id : {}", userId );
        if (userId == null || userId <= 0) {
            throw new EmptyEntityException(MessagesError.ID_IS_INVALID_EQUALS.getMessage() + userId);
        }
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(MessagesError.USER_NOT_FOUND_WITH_ID_EQUALS.getMessage()+userId);
        }
        userRepository.deleteUserById(userId);
        rabbitTemplate.convertAndSend("tasksExchange", "tasksRouting", userId);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) throws UserNotFoundException {
        log.info("Creating new user: {}", userDto.getEmail());
        var validationErrors = UserInputValidation.validate(userDto);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }
        var user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(MessagesError.USER_NOT_FOUND.getMessage()));
        return MappingProfile.mapToUserDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUserByEmail(String email) throws UserNotFoundException {
        log.info("Fetching user by email: {}", email);
        return userRepository.findUserByEmail(email)
                .map(MappingProfile::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException(
                        MessagesError.USER_NOT_FOUND_WITH_EMAIL_EQUALS.getMessage()+email));
    }

    @Override
    public UserResponseDto getUserByUsername(String username) throws UserNotFoundException {
        log.info("Fetching user by username: {}", username);
        return userRepository.findByUserName(username)
                .map(MappingProfile::mapToUserDto)
                //  .map(Optional::of)
                .orElseThrow(() -> new UserNotFoundException(
                        MessagesError.USER_NOT_FOUND_WITH_USERNAME_EQUALS.getMessage()+username));
    }

    @Override
    public UserResponseDto getUserByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        log.info("Fetching user by username and password : {}", username + "Password : *******");
        return userRepository.findByUserNameAndPassword(username, password)
                .map(MappingProfile::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException(MessagesError.USER_NOT_FOUND.getMessage()));
    }

    @Override
    public UserResponseDto getUserByEmailAndPassword(String email, String password) throws UserNotFoundException {
        log.info("Fetching user by email and password : {}", email + "Password : *******");
        return userRepository.findByEmailAndPassword(email, password)
                .map(MappingProfile::mapToUserDto)
                .orElseThrow(() -> new UserNotFoundException(
                        MessagesError.USER_NOT_FOUND.getMessage()));
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
