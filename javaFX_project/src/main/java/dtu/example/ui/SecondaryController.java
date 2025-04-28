package dtu.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private TextField initialsField;

    @FXML
    private void switchToSecondary() throws IOException {
        String initials = initialsField.getText(); // For getting one information to the next scene
        mainMenuController.setUserInitials(initials);
        App.setRoot("mainMenu");
    }
}
