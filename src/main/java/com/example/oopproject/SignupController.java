package com.example.oopproject;

public class SignupController {
    public javafx.scene.control.TextField usernameField;
    public javafx.scene.control.TextField firstNameField;
    public javafx.scene.control.TextField lastNameField;
    public javafx.scene.control.DatePicker dobField;
    public javafx.scene.control.PasswordField passwordField;
    public javafx.scene.control.Label errorLabel;

    public void handleSubmit(javafx.event.ActionEvent actionEvent) {
        System.out.println("Submit Button clicked");
    }
}
