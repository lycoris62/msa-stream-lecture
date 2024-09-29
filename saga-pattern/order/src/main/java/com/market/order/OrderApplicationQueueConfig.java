package com.market.order;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderApplicationQueueConfig {

    private final RabbitmqProperty rabbitmqProperty;

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(rabbitmqProperty.exchange());
    }

    @Bean
    public Queue queueProduct() {
        return new Queue(rabbitmqProperty.queueProduct());
    }

    @Bean
    public Queue queuePayment() {
        return new Queue(rabbitmqProperty.queuePayment());
    }

    @Bean
    public Binding bindingProduct() {
        return BindingBuilder.bind(queueProduct()).to(exchange()).with(rabbitmqProperty.queueProduct());
    }

    @Bean
    public Binding bindingPayment() {
        return BindingBuilder.bind(queuePayment()).to(exchange()).with(rabbitmqProperty.queuePayment());
    }

    // error

    @Bean
    public TopicExchange exchangeErr() {
        return new TopicExchange(rabbitmqProperty.errorExchange());
    }

    @Bean
    public Queue queueErrOrder() {
        return new Queue(rabbitmqProperty.queueErrOrder());
    }

    @Bean
    public Queue queueErrProduct() {
        return new Queue(rabbitmqProperty.queueErrProduct());
    }

    @Bean
    public Binding bindingErrOrder() {
        return BindingBuilder.bind(queueErrOrder()).to(exchangeErr()).with(rabbitmqProperty.queueErrOrder());
    }

    @Bean
    public Binding bindingErrProduct() {
        return BindingBuilder.bind(queueErrProduct()).to(exchangeErr()).with(rabbitmqProperty.queueErrProduct());
    }
}
