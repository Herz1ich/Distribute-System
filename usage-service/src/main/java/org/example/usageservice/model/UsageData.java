package org.example.usageservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage", uniqueConstraints = @UniqueConstraint(columnNames = "hour"))
public class UsageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDateTime hour;

    @Column(nullable = false)
    private double communityProduced = 0.0;

    @Column(nullable = false)
    private double communityUsed = 0.0;

    @Column(nullable = false)
    private double gridUsed = 0.0;

    @Column
    private String userId;

    @Column
    private Double kwh;

    @Column
    private LocalDateTime timestamp;

    public UsageData() {
    }

    public UsageData(LocalDateTime hour) {
        this.hour = hour;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public double getCommunityProduced() {
        return communityProduced;
    }

    public void setCommunityProduced(double communityProduced) {
        this.communityProduced = communityProduced;
    }

    public double getCommunityUsed() {
        return communityUsed;
    }

    public void setCommunityUsed(double communityUsed) {
        this.communityUsed = communityUsed;
    }

    public double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(double gridUsed) {
        this.gridUsed = gridUsed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getKwh() {
        return kwh;
    }

    public void setKwh(Double kwh) {
        this.kwh = kwh;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UsageData{" +
                "hour=" + hour +
                ", produced=" + communityProduced +
                ", used=" + communityUsed +
                ", grid=" + gridUsed +
                ", userId=" + userId +
                ", kwh=" + kwh +
                ", timestamp=" + timestamp +
                '}';
    }
}
