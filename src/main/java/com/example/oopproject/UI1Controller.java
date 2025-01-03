package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UI1Controller {

    public Button selectButton;
    @FXML
    private ImageView thumbnailImage;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label movieLabel;

    @FXML
    private Button backButton; // Declare the back button

    private final List<File> thumbnails = new ArrayList<>();
    private final List<String> movieNames = new ArrayList<>();
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        loadMovieData();
        loadThumbnails();

        if (!thumbnails.isEmpty()) {
            showThumbnail(0);
        }

        updateButtonStates();
    }

    private void loadMovieData() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("movie_database.txt"));
            for (String line : lines) {
                String movieName = line.split("\\|")[0].trim();
                movieNames.add(movieName);
            }
        } catch (IOException e) {
            System.err.println("Error reading movie database: " + e.getMessage());
        }
    }

    private void loadThumbnails() {
        File thumbnailDir = new File("thumbnails");
        if (thumbnailDir.exists() && thumbnailDir.isDirectory()) {
            for (String movieName : movieNames) {
                File thumbnailFile = new File(thumbnailDir, movieName + ".jpg");
                if (thumbnailFile.exists() && thumbnailFile.isFile()) {
                    thumbnails.add(thumbnailFile);
                }
            }
        }
    }

    @FXML
    private void showPrevThumbnail() {
        if (currentIndex > 0) {
            currentIndex--;
            showThumbnail(currentIndex);
            updateButtonStates();
        }
    }

    @FXML
    private void showNextThumbnail() {
        if (currentIndex < thumbnails.size() - 1) {
            currentIndex++;
            showThumbnail(currentIndex);
            updateButtonStates();
        }
    }

    @FXML
    private void handleSelectButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UI2.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 720, 600));
            stage.setTitle("Movie Select Window");

            // Pass data to the second controller
            UI2Controller controller = loader.getController();
            controller.setMovieData(movieNames.get(currentIndex), thumbnails.get(currentIndex));

            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading UI2.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackButton() {
        try {
            // Load the MainMenu.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(loader.load(), 720, 600)); // Set the scene for the stage
            stage.setTitle("Main Menu");

            stage.show(); // Show the stage
        } catch (IOException e) {
            System.err.println("Error loading MainMenu.fxml: " + e.getMessage());
        }
    }

    private void showThumbnail(int index) {
        File file = thumbnails.get(index);
        Image image = new Image(file.toURI().toString());
        thumbnailImage.setImage(image);
        movieLabel.setText(movieNames.get(index));
    }

    private void updateButtonStates() {
        prevButton.setDisable(currentIndex == 0);
        nextButton.setDisable(currentIndex == thumbnails.size() - 1);
    }
}
