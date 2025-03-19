package ua.yatsergray.backend.v2.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.yatsergray.backend.v2.provider.RabbitMQPropertyProvider;

@Configuration
public class RabbitMQConfig {
    private final RabbitMQPropertyProvider rabbitMQPropertyProvider;

    @Autowired
    public RabbitMQConfig(RabbitMQPropertyProvider rabbitMQPropertyProvider) {
        this.rabbitMQPropertyProvider = rabbitMQPropertyProvider;
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(converter());

        return rabbitTemplate;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(rabbitMQPropertyProvider.getExchangeName(), true, false);
    }

    @Bean
    public Queue emailInvitationQueue() {
        return new Queue(rabbitMQPropertyProvider.getEmailInvitationQueueName());
    }

    @Bean
    public Binding emailInvitationBinding() {
        return BindingBuilder
                .bind(emailInvitationQueue())
                .to(exchange())
                .with(rabbitMQPropertyProvider.getEmailInvitationRoutingKeyName());
    }
}
