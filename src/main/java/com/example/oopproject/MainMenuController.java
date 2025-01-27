package com.example.oopproject;
//Error Where ?? (-_-)
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuController {

    public Button selectButton;
    public Button profileButton;
    public Button bookingButton;
    public Button logoutButton;
    public ImageView backgroundImage;
    private User currentUser;

    @FXML
    private Label profileDetailsLabel;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void handleSelectMovies() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui1.fxml"));
            StackPane root = loader.load();
            UI1Controller controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = new Stage();
            stage.setTitle("Select Movies");
            stage.setScene(new Scene(root, 720, 600));
            stage.show();
            Stage currentStage = (Stage) selectButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
            StackPane root = loader.load();
            ProfileController controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = new Stage();
            stage.setTitle("My Profile");
            stage.setScene(new Scene(root, 720, 600));
            stage.show();
            Stage currentStage = (Stage) profileButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBooked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("booking.fxml"));
            AnchorPane root = loader.load();
            BookingController controller = loader.getController();
            controller.setUser(currentUser);
            Stage stage = new Stage();
            stage.setTitle("Booked Movies");
            stage.setScene(new Scene(root, 720, 600));
            stage.show();
            Stage currentStage = (Stage) bookingButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() {
        try {
            currentUser = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 720, 600));
            stage.show();
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void animateButton(javafx.scene.input.MouseEvent event) {
        Button button = (Button) event.getSource();

        // Create a FadeTransition for fading in the button
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), button);
        fadeIn.setToValue(0.8); // Reduce opacity for the fade-in effect

        // Play the fade-in animation
        fadeIn.play();
    }

    @FXML
    public void resetButtonAnimation(javafx.scene.input.MouseEvent event) {
        Button button = (Button) event.getSource();

        // Create a FadeTransition for fading out the button
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), button);
        fadeOut.setToValue(1.0); // Restore full opacity

        // Play the fade-out animation
        fadeOut.play();
    }
}
