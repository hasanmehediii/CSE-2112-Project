package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private Text totalDueText;

    private User currentUser;

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
        populateUserDetails();
    }

    @FXML
    public void initialize() {
        assert firstNameField != null : "fx:id 'firstNameField' was not injected: check your FXML file.";
        assert lastNameField != null : "fx:id 'lastNameField' was not injected: check your FXML file.";
        assert dateOfBirthField != null : "fx:id 'dateOfBirthField' was not injected: check your FXML file.";
        assert usernameField != null : "fx:id 'usernameField' was not injected: check your FXML file.";
        assert totalDueText != null : "fx:id 'totalDueText' was not injected: check your FXML file.";
    }

    private void populateUserDetails() {
        if (currentUser != null) {
            firstNameField.setText(currentUser.firstName());
            lastNameField.setText(currentUser.lastName());
            dateOfBirthField.setText(currentUser.dateOfBirth());
            usernameField.setText(currentUser.username());

            // If you want to handle transactions or total due text, do it here
            double totalDue = currentUser != null ? currentUser.currentBalance() : 0.0;
            totalDueText.setText("Total Due: $" + totalDue);
        }
    }

    @FXML
    public void handleBackButton() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("MainMenu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) backButton.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(loader.load(), 720, 600));
            stage.setTitle("Main Menu");
            stage.setResizable(false);

            // Pass the currentUser to the main menu
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setUser(currentUser);

            stage.show();
        } catch (java.io.IOException e) {
            System.err.println("Error loading MainMenu.fxml: " + e.getMessage());
        }
    }
}
