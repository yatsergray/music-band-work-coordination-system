package ua.yatsergray.backend.v2.service.impl;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.InvitationDTO;
import ua.yatsergray.backend.v2.service.EmailService;
import ua.yatsergray.backend.v2.service.RabbitMQConsumerService;

@Service
@EnableRabbit
public class EmailInvitationRabbitMQConsumerServiceImpl implements RabbitMQConsumerService<InvitationDTO> {
    private final EmailService emailService;

    @Autowired
    public EmailInvitationRabbitMQConsumerServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @RabbitListener(queues = {"${rabbitmq.email.invitation.queue.name}"})
    public void receiveMessage(InvitationDTO message) {
        emailService.sendInvitationEmail(message.getEmail(), message.getToken());
    }
}
