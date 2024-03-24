package org.usermicroservice.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.dto.UserResponseDto;
import org.usermicroservice.entities.User;

public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static User mapToUserEntity(UserRequestDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public static UserResponseDto mapToUserDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }
    static {
        Converter<User, UserResponseDto> userToUserResponseDtoConverter = new Converter<User, UserResponseDto>() {
            public UserResponseDto convert(MappingContext<User, UserResponseDto> context) {
                User source = context.getSource();
                UserResponseDto destination = new UserResponseDto();
                destination.setEmail(source.getEmail());
                destination.setTasks(source.getTasks());
                return destination;
            }
        };
        modelMapper.addConverter(userToUserResponseDtoConverter);
    }

    public static UserResponseDto toUserResponseDto(User user){
        if( user == null) return null;
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setActive(user.getActive());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setLastname(user.getLastname());
        userResponseDto.setTasks(user.getTasks());
        return userResponseDto;
    }
}
