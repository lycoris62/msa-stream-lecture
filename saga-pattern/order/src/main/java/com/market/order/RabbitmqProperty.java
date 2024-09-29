package com.market.order;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message")
public record RabbitmqProperty(
    String exchange,
    Queue queue,
    Err err
) {

    public String queueProduct() {
        return queue.product;
    }

    public String queuePayment() {
        return queue.payment;
    }

    public String errorExchange() {
        return err.exchange;
    }

    public String queueErrOrder() {
        return queue.err.order;
    }

    public String queueErrProduct() {
        return queue.err.product;
    }

    @PostConstruct
    public void init() {
        System.out.println("RabbitmqProperty: " + this);
    }

    record Queue(
        String product,
        String payment,
        Err err
    ) {

        record Err(
            String order,
            String product
        ) {

        }
    }

    record Err(String exchange) {

    }
}
