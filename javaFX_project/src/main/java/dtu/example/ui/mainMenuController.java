package dtu.example.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class mainMenuController {

    @FXML
    private Label initialsLabel;

    private static String userInitials;

    public static void setUserInitials(String initials) {
        userInitials = initials;
    }

    @FXML
    private void initialize() {
        initialsLabel.setText("Welcome, " + userInitials);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}
