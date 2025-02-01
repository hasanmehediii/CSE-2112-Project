package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.scene.Node;
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

    @FXML
    public void handleForgotPassword() {
        String username = usernameField.getText();
        if (username.isEmpty()) {
            errorLabel.setText("Enter username first!");
            fadeErrorLabel();
            return;
        }

        showForgotPasswordPopup(username);
    }

    private void showForgotPasswordPopup(String username) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Recover Password");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #000428, #004e92);"
                + "-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: white;");

        Label instruction = new Label("Enter your Date of Birth (YYYY-MM-DD):");
        instruction.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        // Date of Birth er text field
        TextField dobField = new TextField();
        dobField.setPromptText("YYYY-MM-DD");
        dobField.setPrefWidth(250);
        dobField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: white; -fx-padding: 5; -fx-font-size: 14px;");

        // Recover Password option eita
        Button submitButton = new Button("Recover Password");
        submitButton.setStyle("-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);"
                + "-fx-background-radius: 25; -fx-text-fill: white; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 5); -fx-font-weight: bold;"
                + "-fx-padding: 10 20;");
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: linear-gradient(to right, #00e6ff, #0082ff);"
                + "-fx-background-radius: 25; -fx-text-fill: white; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 5); -fx-font-weight: bold;"
                + "-fx-padding: 10 20;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);"
                + "-fx-background-radius: 25; -fx-text-fill: white; -fx-font-size: 16px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 5); -fx-font-weight: bold;"
                + "-fx-padding: 10 20;"));

        //password ekhane dekhabe
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-font-weight: bold;");
        submitButton.setOnAction(e -> {
            String dob = dobField.getText().trim();
            String password = getPasswordByDOB(username, dob);

            if (password != null) {
                resultLabel.setText("Your Password: " + password);
                resultLabel.setStyle("-fx-text-fill: #38ef7d; -fx-font-size: 16px; -fx-font-weight: bold;");
            } else {
                resultLabel.setText("Incorrect DOB or username!");
                resultLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px; -fx-font-weight: bold;");
            }
        });

        // Close Button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: linear-gradient(to right, #ff7e5f, #ff6347);"
                + "-fx-background-radius: 25; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 5); -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"
                + "-fx-padding: 10 20;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: linear-gradient(to right, #ff927b, #ff7569);"
                + "-fx-background-radius: 25; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 5); -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"
                + "-fx-padding: 10 20;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: linear-gradient(to right, #ff7e5f, #ff6347);"
                + "-fx-background-radius: 25; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 5); -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"
                + "-fx-padding: 10 20;"));
        closeButton.setOnAction(e -> popupStage.close());

        layout.getChildren().addAll(instruction, dobField, submitButton, resultLabel, closeButton);

        Scene scene = new Scene(layout, 400, 250);
        popupStage.setScene(scene);
        popupStage.show();
    }


    private String getPasswordByDOB(String username, String dob) {
        String dataFile = "src/main/java/com/example/oopproject/user_database.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String fileUsername = parts[0].trim();
                    String filePassword = parts[1].trim();
                    String fileDOB = parts[4].trim();

                    if (fileUsername.equals(username) && fileDOB.equals(dob)) {
                        return filePassword;
                    }
                }
            }
        } catch (IOException e) {
            errorLabel.setText("Error reading credentials file.");
        }
        return null;
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
            currentStage.setResizable(false);
            currentStage.show();

        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load Main Menu window. Check console for details.");
            e.printStackTrace();
        }
    }

    private void openAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/admin.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene adminDashboardScene = new Scene(root, 720, 600);
            currentStage.setScene(adminDashboardScene);
            currentStage.setTitle("Admin Window");
            currentStage.setResizable(false);
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
            currentStage.setResizable(false);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            errorLabel.setText("Error: Unable to load Sign-Up window.");
        }
    }

    public void handleBackButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start.fxml"));
            Parent root = fxmlLoader.load();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene loginScene = new Scene(root, 720, 600);
            currentStage.setScene(loginScene);
            currentStage.sizeToScene();
            currentStage.setResizable(false);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error: Unable to load the Login window.");
        }
    }
}
