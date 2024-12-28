package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.io.IOException;

public class StartController {

    @FXML
    private Button startButton;
    @FXML
    private Button aboutButton;
    @FXML
    private Button exitButton;

    @FXML
    private void initialize() {
        FadeTransition fadeStartButton = new FadeTransition(Duration.millis(300), startButton);
        fadeStartButton.setFromValue(1.0);
        fadeStartButton.setToValue(0.5);

        startButton.setOnMouseEntered(event -> fadeStartButton.play());

        startButton.setOnMouseExited(event -> {
            FadeTransition fadeInStartButton = new FadeTransition(Duration.millis(300), startButton);
            fadeInStartButton.setFromValue(0.5);
            fadeInStartButton.setToValue(1.0);
            fadeInStartButton.play();
        });

        FadeTransition fadeAboutButton = new FadeTransition(Duration.millis(300), aboutButton);
        fadeAboutButton.setFromValue(1.0);
        fadeAboutButton.setToValue(0.5);

        aboutButton.setOnMouseEntered(event -> fadeAboutButton.play());

        aboutButton.setOnMouseExited(event -> {
            FadeTransition fadeInAboutButton = new FadeTransition(Duration.millis(300), aboutButton);
            fadeInAboutButton.setFromValue(0.5);
            fadeInAboutButton.setToValue(1.0);
            fadeInAboutButton.play();
        });

        FadeTransition fadeExitButton = new FadeTransition(Duration.millis(300), exitButton);
        fadeExitButton.setFromValue(1.0);
        fadeExitButton.setToValue(0.5);

        exitButton.setOnMouseEntered(event -> fadeExitButton.play());

        exitButton.setOnMouseExited(event -> {
            FadeTransition fadeInExitButton = new FadeTransition(Duration.millis(300), exitButton);
            fadeInExitButton.setFromValue(0.5);
            fadeInExitButton.setToValue(1.0);
            fadeInExitButton.play();
        });
    }

    @FXML
    private void start(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene mainMenuScene = new Scene(root, 720, 600);
            currentStage.setScene(mainMenuScene);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            System.err.println("Error: Unable to load Main Menu window.");
        }
    }

    @FXML
    private void aboutUs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/about.fxml"));
            Parent root = loader.load();

            Stage aboutStage = new Stage();
            aboutStage.setTitle("About Us");
            Scene aboutScene = new Scene(root, 720, 600);
            aboutStage.setScene(aboutScene);
            aboutStage.show();
        } catch (IOException e) {
            System.err.println("Error: Unable to load About Us window.");
        }
    }

    @FXML
    private void exit() {
        System.out.println("Exit button clicked!");
        System.exit(0);
    }
}
