package com.energy.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainApp extends Application {

    private final Label percentageLabel = new Label("Click to fetch energy data.");
    private final Label historicalLabel = new Label();


    @Override
    public void start(Stage primaryStage) {
        Button currentButton = new Button("Load Current Data");
        currentButton.setOnAction(e -> loadCurrentData());

        DatePicker startPicker = new DatePicker();
        startPicker.setPromptText("Start Date");

        DatePicker endPicker = new DatePicker();
        endPicker.setPromptText("End Date");

        Button historicalButton = new Button("Load Historical Data");
        historicalButton.setOnAction(e -> {
            String start = startPicker.getValue() + "T14:00";
            String end = endPicker.getValue() + "T15:00";
            loadHistoricalData(start, end);
        });

        VBox layout = new VBox(10, percentageLabel, currentButton,
                new Label("Start:"), startPicker,
                new Label("End:"), endPicker,
                historicalButton, historicalLabel);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setTitle("Energy Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadCurrentData() {
        try {
            JSONObject data = fetchJson("http://localhost:8080/energy/current");
            String community = data.get("community_depleted") + "% used";
            String grid = data.get("grid_portion") + "%";
            percentageLabel.setText("Community Pool: " + community + "\nGrid Portion: " + grid);
        } catch (Exception e) {
            percentageLabel.setText("Error: " + e.getMessage());
        }
    }

    private void loadHistoricalData(String start, String end) {
        try {
            String url = String.format("http://localhost:8080/energy/historical?start=%s&end=%s", start, end);
            JSONObject data = fetchJson(url);
            String result = String.format("Start: %s\nEnd: %s\nProduced: %.3f kWh\nUsed: %.3f kWh\nGrid Used: %.2f kWh",
                    data.get("start"),
                    data.get("end"),
                    data.getDouble("communityProduced"),
                    data.getDouble("communityUsed"),
                    data.getDouble("gridUsed")
            );
            historicalLabel.setText(result);
        } catch (Exception e) {
            historicalLabel.setText("Error: " + e.getMessage());
        }
    }

    private JSONObject fetchJson(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) response.append(inputLine);
        in.close();

        return new JSONObject(response.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}



