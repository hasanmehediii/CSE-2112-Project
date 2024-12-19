package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

public class LoginController {
    public javafx.scene.control.TextField usernameField;
    public javafx.scene.control.PasswordField passwordField;
    public javafx.scene.control.Label errorLabel;

    @FXML
    public  void handleUserLogin(){
        System.out.println("Login");
    }
    public  void handleAdminLogin(){
        System.out.println("Login");
    }
    @FXML
    public void handleSignUp() {
        try {
            // Load the sign-up FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/signup.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setTitle("SignUp Page");
            Scene signUpScene = new Scene(root);
            currentStage.setScene(signUpScene);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
                errorLabel.setText("Error: Unable to load Sign-Up window.");
        }
    }
}