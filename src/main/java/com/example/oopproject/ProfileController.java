package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableView<String> moviesTable;

    private User currentUser;

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
        populateUserDetails();
    }

    private void populateUserDetails() {
        if (currentUser != null) {
            firstNameField.setText(currentUser.firstName());
            lastNameField.setText(currentUser.lastName());
            dateOfBirthField.setText(currentUser.dateOfBirth());
            usernameField.setText(currentUser.username());
            // You can also populate the movies table with transactions or booked data if needed
        }
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
