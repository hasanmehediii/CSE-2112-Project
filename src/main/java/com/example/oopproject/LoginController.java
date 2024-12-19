package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import javafx.scene.Parent;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void handleUserLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (checkCredentials(username, password)) {
            openMainMenuWindow();
        } else {
            errorLabel.setText("Invalid username or password!");
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
        }
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
        System.out.println("Main Menu Opened");
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/main_menu.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene mainMenuScene = new Scene(root);
            currentStage.setScene(mainMenuScene);
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load Main Menu window.");
        }*/
    }

    private void openAdminDashboard() {
        System.out.println("Admin Dashboard Opened");
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/admin_dashboard.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene adminDashboardScene = new Scene(root);
            currentStage.setScene(adminDashboardScene);
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load Admin Dashboard.");
        }*/
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