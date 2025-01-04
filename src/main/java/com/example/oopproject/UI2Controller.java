package com.example.oopproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UI2Controller {

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

    @FXML
    private TableColumn<TimeSlot, String> priceColumn;

    @FXML
    private Spinner<Integer> ticketSpinner;

    @FXML
    private Button backButton;

    @FXML
    private Button bookTicketButton;

    private final ObservableList<TimeSlot> timeSlots = FXCollections.observableArrayList();

    private String currentUser; // Variable to hold the current user name

    @FXML
    public void initialize() {
        // Set up columns in the TableView
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        totalSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Bind the observable list to the TableView
        timeSlotsTable.setItems(timeSlots);

        // Initialize the Spinner with a default value
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        ticketSpinner.setValueFactory(valueFactory);
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
                    // Ensure the line has enough parts to parse time slots
                    for (int i = 2; i + 2 < parts.length; i += 3) {
                        String time = parts[i].trim();
                        String totalSeats = parts[i + 1].trim();
                        String availableSeats = parts[i + 2].trim();

                        // Use the price from the file (second column)
                        String price = parts[1].trim();

                        timeSlots.add(new TimeSlot(time, totalSeats, availableSeats, price));
                    }
                    break; // Stop searching once the correct movie is found
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading movie database: " + e.getMessage());
        }
    }

    // Set the current user for ticket booking
    public void setUser(String username) {
        this.currentUser = username;
    }

    @FXML
    private void handleBooking() {
        // Get the selected row
        TimeSlot selectedSlot = timeSlotsTable.getSelectionModel().getSelectedItem();
        int ticketsToBook = ticketSpinner.getValue();

        if (selectedSlot != null) {
            int availableSeats = Integer.parseInt(selectedSlot.getAvailableSeats().split(" ")[0]);

            if (ticketsToBook <= availableSeats) {
                // Show confirmation dialog
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Payment Warning");
                confirmationDialog.setHeaderText("Important Notice");
                confirmationDialog.setContentText(
                        "If you don't pay within 24 hours, you will lose access to the booked tickets.\n\nDo you want to confirm the booking?"
                );

                ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirmationDialog.getButtonTypes().setAll(yesButton, noButton);

                // Wait for user action
                confirmationDialog.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        int updatedSeats = availableSeats - ticketsToBook;

                        selectedSlot.setAvailableSeats(updatedSeats + " available");
                        timeSlotsTable.refresh();

                        // Optional: Update database file to persist changes
                        updateDatabase(selectedSlot);

                        Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
                        successDialog.setTitle("Booking Confirmed");
                        successDialog.setHeaderText(null);
                        successDialog.setContentText(
                                "Booking successful!\n" + ticketsToBook + " tickets booked.\nSeats remaining: " + updatedSeats
                        );
                        successDialog.showAndWait();
                    } else {
                        System.out.println("Booking cancelled.");
                    }
                });
            } else {
                Alert warningDialog = new Alert(Alert.AlertType.WARNING);
                warningDialog.setTitle("Insufficient Seats");
                warningDialog.setHeaderText(null);
                warningDialog.setContentText("Not enough seats available. Seats remaining: " + availableSeats);
                warningDialog.showAndWait();
            }
        } else {
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("No Time Slot Selected");
            errorDialog.setHeaderText(null);
            errorDialog.setContentText("Please select a time slot to book tickets.");
            errorDialog.showAndWait();
        }
    }

    private void updateDatabase(TimeSlot updatedSlot) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("movie_database.txt"));
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split("\\|");
                if (parts[0].equalsIgnoreCase(movieLabel.getText())) {
                    // Find the slot and update available seats
                    for (int j = 2; j < parts.length; j += 3) {
                        if (parts[j].trim().equals(updatedSlot.getTime())) {
                            parts[j + 2] = updatedSlot.getAvailableSeats();
                            lines.set(i, String.join("|", parts));
                            break;
                        }
                    }
                }
            }

            Files.write(Paths.get("movie_database.txt"), lines);
        } catch (IOException e) {
            System.err.println("Error updating database: " + e.getMessage());
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
        private String availableSeats;
        private final String price;

        public TimeSlot(String time, String totalSeats, String availableSeats, String price) {
            this.time = time;
            this.totalSeats = totalSeats;
            this.availableSeats = availableSeats;
            this.price = price;
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

        public void setAvailableSeats(String availableSeats) {
            this.availableSeats = availableSeats;
        }

        public String getPrice() {
            return price;
        }
    }
}
