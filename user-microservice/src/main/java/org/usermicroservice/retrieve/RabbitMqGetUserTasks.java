package org.usermicroservice.retrieve;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.usermicroservice.dto.Task;

import java.util.List;
@Component
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMqGetUserTasks {
    @Autowired
    private  RabbitTemplate rabbitTemplate;
    @Value("${user.tasks.queue}")
    private String getUserTasksQueue;

    @Value("${user.tasks.routingKey}")
    private String getUserTasksRoutingKey;



    public List<Task> getUserTasks(Long userId, String getUserTasksRoutingKey) {
        Message responseMessage = rabbitTemplate
                .sendAndReceive("tasksExchange", getUserTasksRoutingKey
                        , rabbitTemplate.getMessageConverter().toMessage(userId, null));
        if (responseMessage != null && responseMessage.getBody() != null) {
            return (List<Task>) rabbitTemplate.getMessageConverter().fromMessage(responseMessage);
        } else {
            return null;
        }
    }

    public List<Task> call(String message, String routingKey) {
        Message responseMessage = rabbitTemplate
                .sendAndReceive("tasksExchange", routingKey, rabbitTemplate
                        .getMessageConverter().toMessage(message, null));
        if (responseMessage != null && responseMessage.getBody() != null) {
            return (List<Task>) rabbitTemplate.getMessageConverter().fromMessage(responseMessage);
        } else {
            return null;
        }
    }
}
