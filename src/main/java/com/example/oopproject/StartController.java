package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class StartController {

    @FXML
    private void start(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oopproject/login.fxml"));
            Parent root = loader.load();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene mainMenuScene = new Scene(root, 720, 600);
            currentStage.setScene(mainMenuScene);
            currentStage.sizeToScene();
            currentStage.show();

        } catch (IOException e) {
            System.err.println("Error: Unable to load Main Menu window.");
            //e.printStackTrace();
        }
    }

    @FXML
    private void exit() {
        System.out.println("Exit button clicked!");
        System.exit(0);
    }
}
