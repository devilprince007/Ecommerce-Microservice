package com.ecommerce.notification.config;

import com.ecommerce.notification.payload.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class OrderEventConsumer {

//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderCreatedEvent orderEvent) {
//        System.out.println("received Order Event: " + orderEvent);
//
//        long orderId = orderEvent.getOrderId();
//        OrderStatus status = orderEvent.getStatus();
//
//        System.out.println("Order Id: " + orderId);
//        System.out.println("Order Status: " + status);
//
//        String userId = orderEvent.getUserId();
//
//        if (userId == null) {
//            System.out.println("userId missing in payload");
//            return;
//        }
//
//        // update database
//        // send notifications
//        // send emails
//        // generate Invoices
//        // send seller notifications
//    }


    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            log.info("Received Order Created Event for Order: {}", event.getOrderId());
            log.info("Received Order Created Event for UserId: {}", event.getUserId());
        };
    }
}
