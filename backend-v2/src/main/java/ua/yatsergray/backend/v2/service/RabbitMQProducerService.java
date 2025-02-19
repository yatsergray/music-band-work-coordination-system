package ua.yatsergray.backend.v2.service;

public interface RabbitMQProducerService<T> {

    void sendMessage(String routingKeyName, T message);
}
