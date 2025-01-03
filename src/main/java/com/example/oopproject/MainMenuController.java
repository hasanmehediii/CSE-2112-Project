package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainMenuController {

    public ImageView backgroundImage;
    public Button startButton;
    public Button settingsButton;
    public Button logoutButton;
    public Button aboutButton;
    @FXML
    private Label profileDetailsLabel;

    @FXML
    public void initialize() {
        profileDetailsLabel.setText("Welcome, User!");
    }

    @FXML
    private void handleSelectMovies() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/ui1.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) profileDetailsLabel.getScene().getWindow();

            Scene loginScene = new Scene(root, 720, 600);
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSettings() {
        System.out.println("Settings button clicked!");
    }

    @FXML
    private void handleAboutUs() {
        System.out.println("About Us button clicked!");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/Login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) profileDetailsLabel.getScene().getWindow();

            Scene loginScene = new Scene(root, 720, 600);
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
