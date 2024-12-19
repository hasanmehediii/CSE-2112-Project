package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/com/example/oopproject/login.fxml"));
            Parent root = loader.load();

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

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required!");
            return;
        }

        saveUserToFile(username, password, firstName, lastName, dob);
        errorLabel.setText("Account created successfully!");
    }

    private void saveUserToFile(String username, String password, String firstName, String lastName, String dob) {
        String dataFile = "src/main/java/com/example/oopproject/user_database.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, true))) {
            writer.write(username + "|" + password + "|" + firstName + "|" + lastName + "|" + dob + "|0.0|No transactions yet");
            writer.newLine();
        } catch (IOException e) {
            errorLabel.setText("Error: Unable to save user data.");
        }
    }
}
