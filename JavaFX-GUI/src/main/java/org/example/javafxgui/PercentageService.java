package org.example.javafxgui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PercentageService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String endpoint = "http://localhost:8085/energy/current";

    public PercentageData getCurrentPercentage() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            System.out.println("Percentage JSON body: " + body);

            JsonNode array = mapper.readTree(body);

            // 从数组中取出最新一条（假设是第一个）
            if (array.isArray() && array.size() > 0) {
                JsonNode latest = array.get(0);
                double percentage = latest.has("percentage") ? latest.get("percentage").asDouble() : 0.0;
                double gridPortion = latest.has("gridPortion") ? latest.get("gridPortion").asDouble() : 0.0;
                String userId = latest.get("userId").asText();
                return new PercentageData(userId, percentage, gridPortion);

            } else {
                System.out.println("Empty or invalid JSON array received.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PercentageData("unknown", 0.0, 0.0);
    }

    public static class PercentageData {
        private final String userId;
        private final double percentage;
        private final double gridPortion;

        public PercentageData(String userId, double percentage, double gridPortion) {
            this.userId = userId;
            this.percentage = percentage;
            this.gridPortion = gridPortion;
        }

        public String getUserId() {
            return userId;
        }

        public double getGridPortion() {
            return gridPortion;
        }

        public double getPercentage() {
            return percentage;
        }
    }
}
