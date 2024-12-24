package com.example.oopproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UI1Controller {

    @FXML
    private ImageView thumbnailImage;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label movieLabel;

    private final List<File> thumbnails = new ArrayList<>();
    private final List<String> movieNames = new ArrayList<>();
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        // Load movie data and thumbnails
        loadMovieData();
        loadThumbnails();

        // Display the first thumbnail and movie name
        if (!thumbnails.isEmpty()) {
            showThumbnail(0);
        }

        // Update button states initially
        updateButtonStates();
    }

    private void loadMovieData() {
        try {
            // Read movie names from text file
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
        // Load thumbnails from the "thumbnails" folder
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
