package com.example.oopproject;

import javafx.scene.control.Button;
public class ProfileController {
    public Button backButton;

    User currentUser;

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void handleBackButton() {
        try {
            // Load the MainMenu.fxml file
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("MainMenu.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) backButton.getScene().getWindow(); // Get the current stage
            stage.setScene(new javafx.scene.Scene(loader.load(), 720, 600)); // Set the scene for the stage
            stage.setTitle("Main Menu");

            // Optionally, you can also pass the currentUser to the main menu if needed
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setUser(currentUser);

            stage.show(); // Show the stage
        } catch (java.io.IOException e) {
            System.err.println("Error loading MainMenu.fxml: " + e.getMessage());
        }
    }
}
