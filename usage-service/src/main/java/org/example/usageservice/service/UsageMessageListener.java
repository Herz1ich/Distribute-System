package org.example.usageservice.service;

import org.example.usageservice.model.UsageData;
import org.example.usageservice.repository.UsageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.example.usageservice.dto.UpdateMessage;

import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Instant;
import java.util.Map;

@Service
public class UsageMessageListener {

    private final UsageRepository usageRepository;
    private final RabbitTemplate rabbitTemplate;

    public UsageMessageListener(UsageRepository usageRepository, RabbitTemplate rabbitTemplate) {
        this.usageRepository = usageRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "producer-queue")
    public void handleProducerMessage(Map<String, Object> message) {
        message.put("type", "PRODUCER");
        message.put("userId", message.get("producerId")); // 兼容字段
        handleUsageMessage(message);
    }

    @RabbitListener(queues = "user-queue")
    public void handleUserMessage(Map<String, Object> message) {
        message.put("type", "USER");
        // user 发来的本来就有 userId
        handleUsageMessage(message);
    }

    public void handleUsageMessage(Map<String, Object> message) {
        System.out.println("Received usage message: " + message);

        try {
            String type = message.get("type").toString();
            String userId = message.get("userId").toString();
            double kwh = Double.parseDouble(message.get("kwh").toString());

            String timestampStr = message.get("timestamp").toString();
            LocalDateTime dateTime = LocalDateTime.parse(timestampStr);
            Instant timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant();

            LocalDateTime hour = dateTime.truncatedTo(ChronoUnit.HOURS);

            // ⭐ 新增逻辑：先查是否存在，避免违反唯一约束插入冲突
            UsageData usage = usageRepository
                    .findByHourBetween(hour, hour.plusHours(1))
                    .stream()
                    .findFirst()
                    .orElse(new UsageData(hour));

            if (type.equalsIgnoreCase("PRODUCER")) {
                usage.setCommunityProduced(usage.getCommunityProduced() + kwh);
            } else if (type.equalsIgnoreCase("USER")) {
                double newUsed = usage.getCommunityUsed() + kwh;
                usage.setCommunityUsed(newUsed);
                if (newUsed > usage.getCommunityProduced()) {
                    usage.setGridUsed(newUsed - usage.getCommunityProduced());
                }
            }

            usage.setUserId(userId);
            usage.setKwh(kwh);
            usage.setTimestamp(dateTime);
            usageRepository.save(usage);  // ✅ 自动 insert or update

            UpdateMessage update = new UpdateMessage();
            update.setUserId(userId);
            update.setProduced(usage.getCommunityProduced());
            update.setUsed(usage.getCommunityUsed());
            update.setGridUsed(usage.getGridUsed());
            update.setTimestamp(timestamp.toEpochMilli());

            rabbitTemplate.convertAndSend("update-exchange", "update-routing-key", update);

            System.out.println("Updated usage and sent update: " + update);


        } catch (Exception e) {
            System.err.println("Error processing usage message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
