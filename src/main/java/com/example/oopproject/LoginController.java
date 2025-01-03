package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.Parent;
import java.io.*;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton, adminLoginButton, signUpButton;

    @FXML
    public void initialize() {
        // Fade in the username, password fields, and buttons
        applyFadeInAnimation(usernameField, passwordField, loginButton, adminLoginButton, signUpButton, errorLabel);

        // Apply translation (slide-in) effect for fields and buttons
        applyTranslateAnimation(usernameField, passwordField, loginButton, adminLoginButton, signUpButton);
    }

    private void applyFadeInAnimation(javafx.scene.Node... nodes) {
        for (javafx.scene.Node node : nodes) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), node);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }
    }

    private void applyTranslateAnimation(javafx.scene.Node... nodes) {
        for (javafx.scene.Node node : nodes) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), node);
            translateTransition.setFromX(-300); // Start from left outside the screen
            translateTransition.setToX(0); // End at the original position
            translateTransition.play();
        }
    }

    @FXML
    public void handleUserLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (checkCredentials(username, password)) {
            openMainMenuWindow();
        } else {
            errorLabel.setText("Invalid username or password!");
            fadeErrorLabel(); // Fade in the error message
        }
    }

    @FXML
    public void handleAdminLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("admin123".equals(username) && "1234".equals(password)) {
            openAdminDashboard();
        } else {
            errorLabel.setText("Wrong Admin Username/Password");
            fadeErrorLabel(); // Fade in the error message
        }
    }

    private void fadeErrorLabel() {
        // Apply fade transition for error label
        FadeTransition fadeError = new FadeTransition(Duration.millis(500), errorLabel);
        fadeError.setFromValue(0.0);
        fadeError.setToValue(1.0);
        fadeError.play();
    }

    private boolean checkCredentials(String username, String password) {
        String dataFile = "src/main/java/com/example/oopproject/user_database.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String fileUsername = parts[0].trim();
                    String filePassword = parts[1].trim();

                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            errorLabel.setText("Error reading credentials file.");
        }
        return false;
    }

    private void openMainMenuWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/MainMenu.fxml"));
            Parent root = loader.load();

            // Load and switch to the new scene
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene mainMenuScene = new Scene(root, 720, 600);
            currentStage.setScene(mainMenuScene);
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            // e.printStackTrace();
            errorLabel.setText("Error: Unable to load Main Menu window. Check console for details.");
        }
    }


    private void openAdminDashboard() {
        System.out.println("Admin Dashboard Opened");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/admin.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene adminDashboardScene = new Scene(root, 720, 600);
            currentStage.setScene(adminDashboardScene);
            currentStage.setTitle("Admin Window");
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load Admin Dashboard.");
        }
    }

    @FXML
    public void handleSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/signup.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene signUpScene = new Scene(root, 720, 600);
            currentStage.setScene(signUpScene);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load Sign-Up window.");
        }
    }

    public record User(String username, String password, String firstName, String lastName, String dateOfBirth,
                       double currentBalance, String lastTransactions) {
    }
}