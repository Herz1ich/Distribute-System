package org.example.usageservice.dto;

public class UpdateMessage {
    private String userId;
    private double produced;
    private double used;
    private double gridUsed;
    private long timestamp;

    public UpdateMessage() {
    }

    public UpdateMessage(String userId, double produced, double used, double gridUsed, long timestamp) {
        this.userId = userId;
        this.produced = produced;
        this.used = used;
        this.gridUsed = gridUsed;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getProduced() {
        return produced;
    }

    public void setProduced(double produced) {
        this.produced = produced;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getGridUsed() {
        return gridUsed;
    }

    public void setGridUsed(double gridUsed) {
        this.gridUsed = gridUsed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
