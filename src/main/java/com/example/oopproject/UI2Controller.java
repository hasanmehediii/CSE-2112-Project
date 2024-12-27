package com.example.oopproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UI2Controller {

    public javafx.scene.control.Button backButton;
    @FXML
    private Label movieLabel;

    @FXML
    private ImageView thumbnailImageView;

    @FXML
    private TableView<TimeSlot> timeSlotsTable;

    @FXML
    private TableColumn<TimeSlot, String> timeSlotColumn;

    @FXML
    private TableColumn<TimeSlot, String> totalSeatsColumn;

    @FXML
    private TableColumn<TimeSlot, String> availableSeatsColumn;

    private final ObservableList<TimeSlot> timeSlots = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns in the TableView
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        totalSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));

        // Bind the observable list to the TableView
        timeSlotsTable.setItems(timeSlots);
    }

    public void setMovieData(String movieName, File thumbnailFile) {
        movieLabel.setText(movieName);
        thumbnailImageView.setImage(new Image(thumbnailFile.toURI().toString()));

        // Load time slots for the movie
        loadTimeSlots(movieName);
    }

    private void loadTimeSlots(String movieName) {
        timeSlots.clear(); // Clear any existing data
        try {
            List<String> lines = Files.readAllLines(Paths.get("movie_database.txt"));
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts[0].equalsIgnoreCase(movieName)) {
                    // Parse the time slots from the line
                    for (int i = 2; i < parts.length; i += 3) {
                        String time = parts[i].trim();
                        String totalSeats = parts[i + 1].trim();
                        String availableSeats = parts[i + 2].trim();

                        timeSlots.add(new TimeSlot(time, totalSeats, availableSeats));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading movie database: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        // Close the current window
        movieLabel.getScene().getWindow().hide();
    }

    // Inner class to represent a time slot
    public static class TimeSlot {
        private final String time;
        private final String totalSeats;
        private final String availableSeats;

        public TimeSlot(String time, String totalSeats, String availableSeats) {
            this.time = time;
            this.totalSeats = totalSeats;
            this.availableSeats = availableSeats;
        }

        public String getTime() {
            return time;
        }

        public String getTotalSeats() {
            return totalSeats;
        }

        public String getAvailableSeats() {
            return availableSeats;
        }
    }
}
