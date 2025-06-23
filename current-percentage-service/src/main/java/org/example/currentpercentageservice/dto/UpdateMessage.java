package org.example.currentpercentageservice.dto;

public class UpdateMessage {
    private String userId; // ✅ 原来是 int，改为 String
    private double produced;
    private double used;
    private double gridUsed;
    private long timestamp;

    // Getter 和 Setter
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

    @Override
    public String toString() {
        return "UpdateMessage{" +
                "userId='" + userId + '\'' +
                ", produced=" + produced +
                ", used=" + used +
                ", gridUsed=" + gridUsed +
                ", timestamp=" + timestamp +
                '}';
    }
}
