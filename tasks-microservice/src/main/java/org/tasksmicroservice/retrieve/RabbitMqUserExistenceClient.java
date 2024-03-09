package org.tasksmicroservice.retrieve;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqUserExistenceClient {
    private final RabbitTemplate rabbitTemplate;
    @Value("${user.existence.queue}")
    private String userExistenceQueue;
    @Autowired
    public RabbitMqUserExistenceClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Boolean checkUserExistence(Long userId) {
        Object response = rabbitTemplate.convertSendAndReceive(userExistenceQueue, userId);
        String stringValue = ""+response;
        return Boolean.parseBoolean(stringValue);
    }
}
