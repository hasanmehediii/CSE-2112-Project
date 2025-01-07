package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController {

    public Button selectButton;
    public Button profileButton;
    public Button bookingButton;
    public Button logoutButton;
    public ImageView backgroundImage;
    private User currentUser;

    @FXML
    private Label profileDetailsLabel;

    public void setUser(User user) {
        this.currentUser = user;
        updateProfileDetails();
    }

    private void updateProfileDetails() {
        if (currentUser != null) {
            profileDetailsLabel.setText("Welcome, " + currentUser.firstName() + " " + currentUser.lastName() +
                    "\nDate of Birth: " + currentUser.dateOfBirth() +
                    "\nDue Amount: $" + currentUser.currentBalance());
        }
    }

    @FXML
    public void handleSelectMovies() {
        System.out.println("Select Movies button clicked");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui1.fxml"));
            javafx.scene.layout.StackPane root = loader.load();
            // Pass the current user data to the new controller
            UI1Controller controller = loader.getController();
            controller.setUser(currentUser);

            // Create a new scene and stage for ui1.fxml
            Stage stage = new Stage();
            stage.setTitle("Select Movies");
            stage.setScene(new Scene(root, 720, 600));  // Set window size
            stage.show();

            // Close current main menu window
            Stage currentStage = (Stage) selectButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleProfile() {
        System.out.println("Profile button clicked");

        try {
            // Load the profile.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
            javafx.scene.layout.StackPane root = loader.load(); // Cast as StackPane to match the FXML root

            // Pass the current user data to the profile controller
            ProfileController controller = loader.getController();
            controller.setUser(currentUser);

            // Create a new scene and stage for profile.fxml
            Stage stage = new Stage();
            stage.setTitle("My Profile");
            stage.setScene(new Scene(root, 720, 600));  // Set window size
            stage.show();

            // Close the current main menu window
            Stage currentStage = (Stage) profileButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBooked() {
        System.out.println("Booked button clicked");
        // Implement functionality for "Booked" button if required
    }

    @FXML
    public void handleLogout() {
        System.out.println("Logout button clicked");

        try {
            // Reset any user data (optional)
            currentUser = null;

            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            AnchorPane root = loader.load();

            // Create a new scene and stage for login.fxml
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 720, 600));  // Set window size
            stage.show();

            // Close current main menu window
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
