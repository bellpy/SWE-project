package dtu.example.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import dtu.example.model.DbContext;

public class LoginController {
    DbContext dbContext = new DbContext();

    @FXML
    private TextField initialsField;

    @FXML
    private Label errorLabel;

    @FXML
    private void switchToMainMenu() throws IOException {
        String initials = initialsField.getText();

        // Validate input initials (non-empty and max 4 letters, alphabetic only)
        if (initials.isEmpty() || initials.length() > 4 || !initials.matches("[a-zA-Z]+")) {
            errorLabel.setText("Please enter up to 4 alphabetic characters.");
            errorLabel.setVisible(true);
            return;
        }
        
        dbContext = new DbContext(); 
        FXMLLoader loader = new FXMLLoader(App.class.getResource("mainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setUserInitials(initials); // Pass initials to the controller
        mainMenuController.setDbContext(dbContext);
        mainMenuController.onDbContextSet();

        // Use the FXMLLoader's root object to set the scene
        Scene scene = new Scene(root);
        App.getPrimaryStage().setScene(scene); // Assuming App has a method to get the primary stage
    }
}
