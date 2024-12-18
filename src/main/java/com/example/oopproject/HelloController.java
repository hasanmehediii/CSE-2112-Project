package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    public javafx.scene.control.TextField usernameField;
    public javafx.scene.control.PasswordField passwordField;
    public javafx.scene.control.Label errorLabel;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public  void handleLogin(){
        System.out.println("Login");
    }
    @FXML
    public  void handleSignUp(){
        System.out.println("Signup");
    }
}