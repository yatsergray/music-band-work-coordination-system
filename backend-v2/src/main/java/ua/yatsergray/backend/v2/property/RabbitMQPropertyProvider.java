package ua.yatsergray.backend.v2.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RabbitMQPropertyProvider {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.email.invitation.queue.name}")
    private String emailInvitationQueueName;

    @Value("${rabbitmq.email.invitation.routing.key.name}")
    private String emailInvitationRoutingKeyName;
}
