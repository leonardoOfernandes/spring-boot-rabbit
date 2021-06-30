package com.example.spring.producer.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerRabbitConfiguration {

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchenge;

    @Value("${spring.rabbitmq.request.deadletter.producer}")
    private String deadletter;

    @Bean
    public DirectExchange exchenge(){
        return new DirectExchange(exchenge);
    }

    @Bean
    public Queue deadletter(){
        return new Queue(deadletter);
    }

    @Bean
    public Queue queue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange",exchenge);
        args.put("x-dead-letter-routing-key",deadletter);
        return new Queue(queue,true,false, false, args);
    }

    @Bean
    public Binding bindingQueue(){
        return BindingBuilder
                .bind(queue())
                .to(exchenge())
                .with(queue);
    }

    @Bean
    public Binding bindingDeadLetter(){
        return BindingBuilder
                .bind(deadletter())
                .to(exchenge())
                .with(deadletter);
    }

}
