package com.example.oopproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.converter.IntegerStringConverter;


public class AdminController {

    @FXML
    private TableView<Movie> movieTable;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> priceColumn;

    @FXML
    private TableColumn<Movie, String> timeslot1Column;

    @FXML
    private TableColumn<Movie, Integer> seats1Column;

    @FXML
    private TableColumn<Movie, String> available1Column;

    @FXML
    private TableColumn<Movie, String> timeslot2Column;

    @FXML
    private TableColumn<Movie, Integer> seats2Column;

    @FXML
    private TableColumn<Movie, String> available2Column;

    @FXML
    private TableColumn<Movie, String> timeslot3Column;

    @FXML
    private TableColumn<Movie, Integer> seats3Column;

    @FXML
    private TableColumn<Movie, String> available3Column;

    private ObservableList<Movie> movieList;
    private final String FILE_PATH = "movie_database.txt";

    @FXML
    public void initialize() {
        movieList = FXCollections.observableArrayList(loadMoviesFromFile());

        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());
        timeslot1Column.setCellValueFactory(data -> data.getValue().timeslot1Property());
        seats1Column.setCellValueFactory(data -> data.getValue().seats1Property().asObject());
        available1Column.setCellValueFactory(data -> data.getValue().available1Property());

        timeslot2Column.setCellValueFactory(data -> data.getValue().timeslot2Property());
        seats2Column.setCellValueFactory(data -> data.getValue().seats2Property().asObject());
        available2Column.setCellValueFactory(data -> data.getValue().available2Property());

        timeslot3Column.setCellValueFactory(data -> data.getValue().timeslot3Property());
        seats3Column.setCellValueFactory(data -> data.getValue().seats3Property().asObject());
        available3Column.setCellValueFactory(data -> data.getValue().available3Property());

        movieTable.setItems(movieList);

        movieTable.setEditable(true);
        setupEditableColumns();
    }

    private void setupEditableColumns() {
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setOnEditCommit(event -> event.getRowValue().setTitle(event.getNewValue()));

        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setOnEditCommit(event -> event.getRowValue().setPrice(event.getNewValue()));

        timeslot1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        timeslot1Column.setOnEditCommit(event -> event.getRowValue().setTimeslot1(event.getNewValue()));

        timeslot2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        timeslot2Column.setOnEditCommit(event -> event.getRowValue().setTimeslot2(event.getNewValue()));

        timeslot3Column.setCellFactory(TextFieldTableCell.forTableColumn());
        timeslot3Column.setOnEditCommit(event -> event.getRowValue().setTimeslot3(event.getNewValue()));

        available1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        available1Column.setOnEditCommit(event -> event.getRowValue().setAvailable1(event.getNewValue()));

        available2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        available2Column.setOnEditCommit(event -> event.getRowValue().setAvailable2(event.getNewValue()));

        available3Column.setCellFactory(TextFieldTableCell.forTableColumn());
        available3Column.setOnEditCommit(event -> event.getRowValue().setAvailable3(event.getNewValue()));

        // Integer-based columns
        seats1Column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        seats1Column.setOnEditCommit(event -> event.getRowValue().setSeats1(event.getNewValue()));

        seats2Column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        seats2Column.setOnEditCommit(event -> event.getRowValue().setSeats2(event.getNewValue()));

        seats3Column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        seats3Column.setOnEditCommit(event -> event.getRowValue().setSeats3(event.getNewValue()));
    }

    @FXML
    private void onAddMovie() {
        Movie newMovie = new Movie("New Movie", "0.0", "Time 1", 10, "Available", "Time 2", 10, "Available", "Time 3", 10, "Available");
        movieList.add(newMovie);
        movieTable.getSelectionModel().select(newMovie);
    }

    @FXML
    private void onDeleteMovie() {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieList.remove(selectedMovie);
        } else {
            showAlert("No Selection", "Please select a movie to delete.");
        }
    }

    @FXML
    private void onSaveChanges() {
        saveMoviesToFile(new ArrayList<>(movieList));
        showAlert("Success", "Changes saved to the file.");
    }

    private List<Movie> loadMoviesFromFile() {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 11) {
                        String title = parts[0].trim();
                        String price = parts[1].trim();
                        String timeslot1 = parts[2].trim();
                        int seats1 = Integer.parseInt(parts[3].trim());
                        String available1 = parts[4].trim();
                        String timeslot2 = parts[5].trim();
                        int seats2 = Integer.parseInt(parts[6].trim());
                        String available2 = parts[7].trim();
                        String timeslot3 = parts[8].trim();
                        int seats3 = Integer.parseInt(parts[9].trim());
                        String available3 = parts[10].trim();

                        movies.add(new Movie(title, price, timeslot1, seats1, available1, timeslot2, seats2, available2, timeslot3, seats3, available3));
                    } else {
                        System.err.println("Skipped invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load movie data: " + e.getMessage());
            System.err.println("Error reading file: " + e.getMessage());
        }
        return movies;
    }

    private void saveMoviesToFile(List<Movie> movies) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Movie movie : movies) {
                writer.write(String.join("|", movie.getTitle(), movie.getPrice(),
                        movie.getTimeslot1(), String.valueOf(movie.getSeats1()), movie.getAvailable1(),
                        movie.getTimeslot2(), String.valueOf(movie.getSeats2()), movie.getAvailable2(),
                        movie.getTimeslot3(), String.valueOf(movie.getSeats3()), movie.getAvailable3()));
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to save file: " + e.getMessage());
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleLogout(javafx.event.ActionEvent actionEvent) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("login.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Login");
            stage.setScene(new javafx.scene.Scene(root, 720, 600));
            stage.setResizable(false);
            stage.show();

            javafx.stage.Stage currentStage = (javafx.stage.Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert("Error", "Unable to load the login screen: " + e.getMessage());
        }
    }

}
