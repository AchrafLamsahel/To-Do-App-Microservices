package org.tasksmicroservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.tasksmicroservice.dto.TaskRequestDto;
import org.tasksmicroservice.enumerations.Status;
import org.tasksmicroservice.service.ITaskService;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class TasksMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksMicroserviceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ITaskService iTaskService){
        return args -> {
            LocalDate localDate = LocalDate.of(2024, Month.MARCH, 20);

            // Convertir la date en java.util.Date
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            TaskRequestDto taskRequestDto = new TaskRequestDto(1L,"Task 1","task for today",
                    1L, Status.IN_PROGRESS,date);
            iTaskService.createTask(taskRequestDto);
        };
    }

}
