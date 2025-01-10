package com.example.oopproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ProfileController {
    @FXML
    private Button backButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField dateOfBirthField;
    @FXML
    private TextField usernameField;
    @FXML
    private TableView<MovieBooking> moviesTable;
    @FXML
    private TableColumn<MovieBooking, String> movieColumn;
    @FXML
    private TableColumn<MovieBooking, Integer> ticketsColumn;
    @FXML
    private Text totalDueText;

    private User currentUser;

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
        populateUserDetails();
    }

    @FXML
    public void initialize() {
        assert moviesTable != null : "fx:id 'moviesTable' was not injected: check your FXML file.";
        assert movieColumn != null : "fx:id 'movieColumn' was not injected: check your FXML file.";
        assert ticketsColumn != null : "fx:id 'ticketsColumn' was not injected: check your FXML file.";
        assert totalDueText != null : "fx:id 'totalDueText' was not injected: check your FXML file.";
    }


    private void populateUserDetails() {
        if (currentUser != null) {
            firstNameField.setText(currentUser.firstName());
            lastNameField.setText(currentUser.lastName());
            dateOfBirthField.setText(currentUser.dateOfBirth());
            usernameField.setText(currentUser.username());

            // Populate movies table
            populateMoviesTable();
        }
    }
    private void populateMoviesTable() {
        ObservableList<MovieBooking> bookings = FXCollections.observableArrayList();

        if (currentUser != null) {
            System.out.println("Current User Details: " + currentUser);
            System.out.println("Last Transactions: " + currentUser.lastTransactions());

            String transactions = currentUser.lastTransactions();
            if (transactions != null && !transactions.trim().isEmpty() && !transactions.equalsIgnoreCase("None")) {
                String[] movies = transactions.split(",\\s*");
                for (String movieEntry : movies) {
                    try {
                        String[] details = movieEntry.split(" \\(");
                        if (details.length == 2) {
                            String movieName = details[0].trim();
                            int tickets = Integer.parseInt(details[1].replace(" tickets)", "").trim());
                            bookings.add(new MovieBooking(movieName, tickets));
                            System.out.println("Parsed Movie: " + movieName + ", Tickets: " + tickets);
                        } else {
                            System.err.println("Skipping invalid movie entry: " + movieEntry);
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing movie entry: " + movieEntry + " - " + e.getMessage());
                    }
                }
            } else {
                System.out.println("No transactions to display.");
            }
        } else {
            System.err.println("currentUser is null.");
        }

        System.out.println("Bookings to display in table: " + bookings);

        // Ensure the column names match the MovieBooking class properties
        movieColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        ticketsColumn.setCellValueFactory(new PropertyValueFactory<>("tickets"));

        // Clear existing items and update the table
        moviesTable.getItems().clear();
        moviesTable.setItems(bookings);

        // Update the total due text
        double totalDue = currentUser != null ? currentUser.currentBalance() : 0.0;
        totalDueText.setText("Total Due: $" + totalDue);

        // Debugging logs
        System.out.println("Table updated successfully.");
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

        public record MovieBooking(String movieName, int tickets) {
    }
}
