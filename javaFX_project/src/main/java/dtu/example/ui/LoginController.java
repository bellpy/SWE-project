package dtu.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField initialsField;

    @FXML
    private void switchToSecondary() throws IOException {
        String initials = initialsField.getText(); // For getting one information to the next scene
        //MainMenuController.setUserInitials(initials);
        App.setRoot("mainMenu");
    }
}
