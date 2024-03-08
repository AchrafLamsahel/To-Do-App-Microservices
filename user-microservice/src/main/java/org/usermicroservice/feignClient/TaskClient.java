package org.usermicroservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.usermicroservice.dto.Task;
import org.usermicroservice.exceptions.UserNotFoundException;
import java.util.List;

@FeignClient(name="TASKS-SERVICE")
public interface TaskClient {
    @GetMapping("/api/v1/tasks/user/{userId}")
    List<Task> findByUserId(@PathVariable("userId") Long userId) throws UserNotFoundException;
    @DeleteMapping("/api/v1/tasks/{id}")
    String deleteTask(@PathVariable Long id) throws UserNotFoundException;

}
