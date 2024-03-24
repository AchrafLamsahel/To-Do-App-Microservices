package org.usermicroservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.dto.UserResponseDto;
import org.usermicroservice.entities.User;
import org.usermicroservice.repositories.UserRepository;
import org.usermicroservice.service.IUserService;

import java.util.Optional;

@SpringBootApplication
@EnableFeignClients
public class UserMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner startApp(IUserService iUserService, UserRepository userRepository) {
        return args -> {
            UserRequestDto userRequestDto = new UserRequestDto(1L,"Achraf","Lamsahel",
                    "AchrafLamsahel","Advanced123","Achraflamsahel1@gmail.com",null);
            iUserService.createUser(userRequestDto);
            UserResponseDto user =iUserService.getUserByUsername("AchrafLamsahel");
            System.out.println(user.getActive());
            System.out.println(user.getId());
            System.out.println(user.getRole());
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getTasks());



        };
    }

}
