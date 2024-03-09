package org.tasksmicroservice.retrieve;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.tasksmicroservice.service.ITaskService;

import java.util.List;

@Component
@AllArgsConstructor
public class RabbitMqGetUserTasks {
    private final ITaskService iTaskService;

    @RabbitListener(queues = "${user.tasks.queue}")
    @SendTo("${user.tasksResponse.queue}")
    public Object getUserTasks(Long userId) {
        return iTaskService.findByUserId(userId);
    }
}
