package com.market.order;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final RabbitmqProperty rabbitmqProperty;
    private final RabbitTemplate rabbitTemplate;
    private final Map<UUID, Order> orderStore = new HashMap<>();

    public Order createOrder(OrderEndpoint.OrderRequestDto orderRequestDto) {

        Order order = orderRequestDto.toOrder();
        orderStore.put(order.getOrderId(), order);

        DeliveryMessage deliveryMessage = orderRequestDto.toDeliveryMessage(order.getOrderId());
        rabbitTemplate.convertAndSend(rabbitmqProperty.queueProduct(), deliveryMessage);

        return order;
    }

    public Order getOrder(UUID orderId) {
        return orderStore.get(orderId);
    }

    public void rollbackOrder(DeliveryMessage deliveryMessage) {
        Order order = orderStore.get(deliveryMessage.getOrderId());
        order.cancelOrder(deliveryMessage.getErrorType());
    }
}
