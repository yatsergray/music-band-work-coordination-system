package ua.yatsergray.backend.v2.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.provider.RabbitMQPropertyProvider;
import ua.yatsergray.backend.v2.service.RabbitMQProducerService;

@Service
public class RabbitMQProducerServiceImpl<T> implements RabbitMQProducerService<T> {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQPropertyProvider rabbitMQPropertyProvider;

    @Autowired
    public RabbitMQProducerServiceImpl(RabbitTemplate rabbitTemplate, RabbitMQPropertyProvider rabbitMQPropertyProvider) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQPropertyProvider = rabbitMQPropertyProvider;
    }

    @Override
    public void sendMessage(String routingKeyName, T message) {
        rabbitTemplate.convertAndSend(rabbitMQPropertyProvider.getExchangeName(), routingKeyName, message);
    }
}
