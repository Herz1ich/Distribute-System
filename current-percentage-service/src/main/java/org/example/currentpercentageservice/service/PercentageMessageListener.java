package org.example.currentpercentageservice.service;

import org.example.currentpercentageservice.model.PercentageData;
import org.example.currentpercentageservice.repository.PercentageRepository;
import org.example.currentpercentageservice.dto.UpdateMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class PercentageMessageListener {

    private final PercentageRepository percentageRepository;

    public PercentageMessageListener(PercentageRepository percentageRepository) {
        this.percentageRepository = percentageRepository;
    }

    @RabbitListener(queues = "update-queue")
    public void handleUpdateMessage(UpdateMessage message) {
        System.out.println("Received update message: " + message);

        double produced = message.getProduced();
        double used = message.getUsed();
        double gridUsed = message.getGridUsed();

        double communityDepleted = (used > produced) ? 100.0 : (used / produced) * 100.0;
        double gridPortion = (used > 0) ? (gridUsed / used) * 100.0 : 0.0;

        LocalDateTime hour = Instant.ofEpochMilli(message.getTimestamp())
                .atZone(ZoneId.systemDefault())
                .withMinute(0).withSecond(0).withNano(0)
                .toLocalDateTime();

        // 检查该小时是否已存在记录
        PercentageData data = percentageRepository.findByHour(hour).orElse(new PercentageData());
        data.setHour(hour);
        data.setUserId(message.getUserId());
        data.setCommunityDepleted(communityDepleted);
        data.setGridPortion(gridPortion);

        data.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getTimestamp()), ZoneId.systemDefault()));
        data.setPercentage(communityDepleted);

        percentageRepository.save(data);

        System.out.println("Saved percentage: " + communityDepleted + "% depleted, grid portion: " + gridPortion + "%");
    }

}
