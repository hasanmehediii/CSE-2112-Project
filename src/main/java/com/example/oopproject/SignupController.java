package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/login.fxml"));
            Parent root = loader.load();

            // Get current stage and set new scene
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene loginScene = new Scene(root);
            currentStage.setScene(loginScene);
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load the Login window.");
        }
    }
    @FXML
    public void handleSubmit() {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dob = dobField.getValue() != null ? dobField.getValue().toString() : "";
        String password = passwordField.getText();

        // Validate input fields
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required!");
            return;
        }

        String dataFile = "src/main/java/com/example/oopproject/user_database.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                if (userData[0].equals(username)) {
                    errorLabel.setText("This username is already registered!");
                    return;
                }
            }
        } catch (IOException e) {
            errorLabel.setText("Error reading user data.");
            return;
        }

        // Save user data to file
        String userData = username + "|" + password + "|" + firstName + "|" + lastName + "|" + dob + "|0.0|None";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, true))) {
            writer.write(userData);
            writer.newLine();
        } catch (IOException e) {
            errorLabel.setText("Error saving user data.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene loginScene = new Scene(root, 720, 600);
            currentStage.setScene(loginScene);
            currentStage.sizeToScene();
            currentStage.show();
        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load Login window.");
        }
    }

}
