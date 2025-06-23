package org.example.javafxgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class JavaFxGuiApplication extends Application {

    private final PercentageService percentageService = new PercentageService();
    private final HistoryService historyService = new HistoryService();

    private Label communityPoolLabel = new Label("Community Pool: -- % used");
    private Label gridPortionLabel = new Label("Grid Portion: -- %");
    private Label producedLabel = new Label("Community produced: --");
    private Label usedLabel = new Label("Community used: --");
    private Label gridUsedLabel = new Label("Grid used: --");

    @Override
    public void start(Stage primaryStage) {
        // ====== Top percentage area ======
        Button refreshButton = new Button("refresh");
        refreshButton.setOnAction(e -> loadCurrentPercentages());

        VBox topBox = new VBox(5, communityPoolLabel, gridPortionLabel, refreshButton);
        topBox.setPadding(new Insets(10));
        topBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // ====== Bottom historical area ======
        Label startLabel = new Label("Start");
        DatePicker startPicker = new DatePicker(LocalDate.now().minusDays(7));
        Label endLabel = new Label("End");
        DatePicker endPicker = new DatePicker(LocalDate.now());

        Button showDataButton = new Button("show data");
        showDataButton.setOnAction(e -> loadHistoryData(startPicker.getValue(), endPicker.getValue()));

        GridPane timeGrid = new GridPane();
        timeGrid.setHgap(10);
        timeGrid.setVgap(5);
        timeGrid.add(startLabel, 0, 0);
        timeGrid.add(startPicker, 1, 0);
        timeGrid.add(endLabel, 0, 1);
        timeGrid.add(endPicker, 1, 1);
        timeGrid.add(showDataButton, 0, 2, 2, 1);

        VBox resultBox = new VBox(5, producedLabel, usedLabel, gridUsedLabel);
        VBox bottomBox = new VBox(10, timeGrid, resultBox);
        bottomBox.setPadding(new Insets(10));
        bottomBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // Layout all
        VBox root = new VBox(15, topBox, bottomBox);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 350);
        primaryStage.setTitle("Energy Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadCurrentPercentages(); // 初始化加载
    }

    private void loadCurrentPercentages() {
        var data = percentageService.getCurrentPercentage();
        communityPoolLabel.setText("Community Pool: " + String.format("%.2f", data.getPercentage()) + "% used");
        gridPortionLabel.setText("Grid Portion: " + String.format("%.2f", data.getGridPortion()) + "%");
    }


    private void loadHistoryData(LocalDate start, LocalDate end) {
        List<HistoryService.HistoryData> rawData = historyService.getHistoryData(start, end);

        double sumProduced = 0.0;
        double sumUsed = 0.0;
        double sumGrid = 0.0;

        for (HistoryService.HistoryData entry : rawData) {
            try {
                sumProduced += Double.parseDouble(entry.communityProducedProperty().get().replace(" kWh", ""));
                sumUsed += Double.parseDouble(entry.communityUsedProperty().get().replace(" kWh", ""));
                sumGrid += Double.parseDouble(entry.gridUsedProperty().get().replace(" kWh", ""));
            } catch (Exception e) {
                System.err.println("Error parsing data: " + e.getMessage());
            }
        }

        producedLabel.setText("Community produced: " + String.format("%.3f", sumProduced) + " kWh");
        usedLabel.setText("Community used: " + String.format("%.3f", sumUsed) + " kWh");
        gridUsedLabel.setText("Grid used: " + String.format("%.2f", sumGrid) + " kWh");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
