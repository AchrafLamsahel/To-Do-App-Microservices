package org.usermicroservice.retrieve;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.usermicroservice.repositories.UserRepository;

@Component
@AllArgsConstructor
public class UserExistenceListener {
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${user.existence.queue}")
    @SendTo("${user.existence.response.queue}")
    public String checkUserExistence(Long userId) {
        String response = String.valueOf(userRepository.existsById(userId));
        return response;
    }
}
