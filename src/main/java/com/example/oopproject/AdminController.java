package com.example.oopproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.stream.Collectors;

public class AdminController {

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TableColumn<Movie, String> movieNameColumn;

    @FXML
    private TableColumn<Movie, Double> ticketPriceColumn;

    @FXML
    private TableColumn<Movie, String> timeSlotsColumn;

    @FXML
    private TableColumn<Movie, Integer> seatsColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button removeButton;

    private final ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private final String DATABASE_FILE = "movie_database.txt";

    @FXML
    public void initialize() {
        configureTableColumns();
        loadMovieData();
        movieTableView.setItems(movieList);

        // Set button actions
        addButton.setOnAction(e -> addMovie());
        editButton.setOnAction(e -> editMovie());
        removeButton.setOnAction(e -> removeMovie());
    }

    // Configure TableView columns
    private void configureTableColumns() {
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        ticketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        timeSlotsColumn.setCellValueFactory(new PropertyValueFactory<>("timeSlots"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
    }

    // Load movie data from the file
    private void loadMovieData() {
        movieList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Movie movie = parseMovieData(line);
                if (movie != null) {
                    movieList.add(movie);
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load movie data from file.");
        }
    }

    // Parse a line of movie data from the database
    private Movie parseMovieData(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 3) {
                String movieName = parts[0];
                String[] priceAndSeats = parts[1].split("\\+");
                double ticketPrice = Double.parseDouble(priceAndSeats[0]);
                int seats = Integer.parseInt(priceAndSeats[1]);
                String timeSlots = parts[2];
                return new Movie(movieName, ticketPrice, timeSlots, seats);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid data format in database.");
        }
        return null;
    }

    // Save all movies to the database
    private void saveMovieData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
            for (Movie movie : movieList) {
                writer.write(movie.toDatabaseFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to save movie data to file.");
        }
    }

    // Add a new movie
    private void addMovie() {
        Movie newMovie = showMovieDialog(null);
        if (newMovie != null) {
            movieList.add(newMovie);
            saveMovieData();
        }
    }

    // Edit an existing movie
    private void editMovie() {
        Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            Movie updatedMovie = showMovieDialog(selectedMovie);
            if (updatedMovie != null) {
                int index = movieList.indexOf(selectedMovie);
                movieList.set(index, updatedMovie);
                saveMovieData();
            }
        } else {
            showAlert("No movie selected", "Please select a movie to edit.");
        }
    }

    // Remove an existing movie
    private void removeMovie() {
        Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            movieList.remove(selectedMovie);
            saveMovieData();
        } else {
            showAlert("No movie selected", "Please select a movie to remove.");
        }
    }

    // Show dialog to add/edit a movie
    private Movie showMovieDialog(Movie movie) {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle(movie == null ? "Add Movie" : "Edit Movie");

        VBox vbox = new VBox(10);
        TextField nameField = new TextField(movie == null ? "" : movie.getMovieName());
        TextField priceField = new TextField(movie == null ? "" : String.valueOf(movie.getTicketPrice()));
        TextField timeSlotField = new TextField(movie == null ? "" : movie.getTimeSlots());
        TextField seatsField = new TextField(movie == null ? "" : String.valueOf(movie.getSeats()));

        vbox.getChildren().addAll(
                new Label("Movie Name:"), nameField,
                new Label("Ticket Price:"), priceField,
                new Label("Time Slots (e.g., 10:00 AM+50,1:00 PM+40):"), timeSlotField,
                new Label("Seats:"), seatsField
        );

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    String timeSlots = timeSlotField.getText();
                    int seats = Integer.parseInt(seatsField.getText());
                    return new Movie(name, price, timeSlots, seats);
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter valid data.");
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    // Show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
