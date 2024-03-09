package org.usermicroservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.service.IUserService;

@SpringBootApplication
@EnableFeignClients
public class UserMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner startApp(IUserService iUserService) {
        return args -> {
            UserRequestDto userRequestDto = new UserRequestDto(1L,"Achraf","Lamsahel",
                    "AchrafLansahel","Advanced123","Achraflamsahel1@gmail.com",null);
            iUserService.createUser(userRequestDto);
        };
    }

}
