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
        applyFadeInAnimation(usernameField, passwordField, loginButton, adminLoginButton, signUpButton, errorLabel);
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
            translateTransition.setFromX(-300);
            translateTransition.setToX(0);
            translateTransition.play();
        }
    }

    @FXML
    public void handleUserLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User loggedInUser = checkCredentials(username, password);
        if (loggedInUser != null) {
            openMainMenuWindow(loggedInUser);
        } else {
            errorLabel.setText("Invalid username or password!");
            fadeErrorLabel();
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
            fadeErrorLabel();
        }
    }

    private void fadeErrorLabel() {
        FadeTransition fadeError = new FadeTransition(Duration.millis(500), errorLabel);
        fadeError.setFromValue(0.0);
        fadeError.setToValue(1.0);
        fadeError.play();
    }

    private User checkCredentials(String username, String password) {
        String dataFile = "src/main/java/com/example/oopproject/user_database.txt";
        //username|password|firstname|lastname|DOB|Due|Tickets
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String fileUsername = parts[0].trim();
                    String filePassword = parts[1].trim();

                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        String firstName = parts[2].trim();
                        String lastName = parts[3].trim();
                        String dateOfBirth = parts[4].trim();
                        double currentBalance = Double.parseDouble(parts[5].trim());
                        return new User(fileUsername, filePassword, firstName, lastName, dateOfBirth, currentBalance, "");
                    }
                }
            }
        } catch (IOException e) {
            errorLabel.setText("Error reading credentials file.");
        }
        return null;
    }

    private void openMainMenuWindow(User loggedInUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();

            mainMenuController.setUser(loggedInUser);

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene mainMenuScene = new Scene(root, 720, 600);
            currentStage.setScene(mainMenuScene);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load Main Menu window. Check console for details.");
            e.printStackTrace();
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
}
