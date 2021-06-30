package com.example.spring.consumer.amqp.implementation;

import com.example.spring.consumer.amqp.AmqpConsumer;
import com.example.spring.consumer.dto.Message;
import com.example.spring.consumer.service.ConsumerService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRabbitMQ implements AmqpConsumer<Message> {

    @Autowired
    private ConsumerService service;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.request.routing-key.producer}")
    public void consumer(Message message) {

        try {
            service.action(message);
        }catch (Exception ex){
            throw new AmqpRejectAndDontRequeueException(ex);
        }


    }
}
