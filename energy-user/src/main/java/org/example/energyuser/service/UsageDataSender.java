package org.example.energyuser.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UsageDataSender {

    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();

    @Value("${user.exchange}")
    private String exchange;

    @Value("${user.routing-key}")
    private String routingKey;

    public UsageDataSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    @Async
    public void startSendingMessages() {
        new Thread(() -> {
            while (true) {
                try {
                    Map<String, Object> message = new HashMap<>();
                    String userId = "user" + (1 + random.nextInt(5)); // user1 to user5
                    message.put("userId", userId);
                    message.put("association", "COMMUNITY");
                    message.put("type", "USER");
                    message.put("kwh", 1 + (1.5 * random.nextDouble()));
                    message.put("timestamp", LocalDateTime.now().toString());


                    System.out.println("Sending user message: " + message);
                    rabbitTemplate.convertAndSend(exchange, routingKey, message);

                    int delay = 1000 + random.nextInt(4000); // 1-5 秒之间
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }
}




