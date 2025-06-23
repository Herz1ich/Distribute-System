package org.example.javafxgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HistoryService {
    private static final String BASE_URL = "http://localhost:8081/energy/historical";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<HistoryData> getHistoryData(LocalDate startDate, LocalDate endDate) {
        List<HistoryData> result = new ArrayList<>();

        try {
            String url = String.format("%s?start=%sT00:00:00&end=%sT23:59:59", BASE_URL, startDate, endDate);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Usage JSON body:" + response.body());

            JsonNode json = mapper.readTree(response.body());

            double totalProduced = 0.0;
            double totalUsed = 0.0;
            double totalGridUsed = 0.0;

            for (JsonNode node : json) {
                totalProduced += safeGetAsDouble(node, "communityProduced");
                totalUsed += safeGetAsDouble(node, "communityUsed");
                totalGridUsed += safeGetAsDouble(node, "gridUsed");
            }

            HistoryData hd = new HistoryData(
                    String.format("%.3f", totalProduced),
                    String.format("%.3f", totalUsed),
                    String.format("%.3f", totalGridUsed)
            );
            result.add(hd);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private double safeGetAsDouble(JsonNode node, String fieldName) {
        JsonNode value = node.get(fieldName);
        return (value != null && value.isNumber()) ? value.asDouble() : 0.0;
    }



    public static class HistoryData {
        private final StringProperty communityProduced;
        private final StringProperty communityUsed;
        private final StringProperty gridUsed;

        public HistoryData(String produced, String used, String grid) {
            this.communityProduced = new SimpleStringProperty(produced);
            this.communityUsed = new SimpleStringProperty(used);
            this.gridUsed = new SimpleStringProperty(grid);
        }

        public StringProperty communityProducedProperty() {
            return communityProduced;
        }

        public StringProperty communityUsedProperty() {
            return communityUsed;
        }

        public StringProperty gridUsedProperty() {
            return gridUsed;
        }
    }
}
