package com.example.oopproject;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.io.*;

public class BookingController {

    @FXML
    private TableView<String[]> bookingTable;

    @FXML
    private TableColumn<String[], String> movieNameColumn;

    @FXML
    private TableColumn<String[], String> ticketCountColumn;

    @FXML
    private Button backButton;

    private User currentUser;

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
        loadBookings();
    }

    private void loadBookings() {
        ObservableList<String[]> bookings = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/example/oopproject/user_database.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equals(currentUser.getUsername())) {
                    String bookingDetails = data[data.length - 1];
                    if (!"None".equals(bookingDetails)) {
                        String[] bookingsArray = bookingDetails.split(", ");
                        for (String booking : bookingsArray) {
                            String[] details = booking.split(" \\(");
                            String movieName = details[0];
                            String tickets = details[1].replaceAll("\\D", "");
                            bookings.add(new String[]{movieName, tickets});
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bookingTable.setItems(bookings);
    }

    @FXML
    public void initialize() {
        movieNameColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue()[0])
        );
        ticketCountColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue()[1])
        );
    }

    @FXML
    public void handleBackButton() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("MainMenu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) backButton.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 720, 600));
            stage.setTitle("Main Menu");

            // Pass the currentUser to the main menu
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setUser(currentUser);

            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error loading MainMenu.fxml: " + e.getMessage());
        }
    }
}
