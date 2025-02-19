package ua.yatsergray.backend.v2.service;

public interface RabbitMQConsumerService<T> {

    void receiveMessage(T message);
}
