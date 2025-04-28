package dtu.example.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {

   // @FXML
   // private Label initialsLabel;

    //private static String userInitials;

   // public static void setUserInitials(String initials) {
   //     userInitials = initials;
   // }

   @FXML
    private void openCreateProjectPopup() throws IOException {
        Parent popupContent = App.loadFXML("CreateProject"); // ← reuse App's loadFXML method!

        Stage popupStage = new Stage();
        popupStage.setTitle("Create Project");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(popupContent));
        popupStage.showAndWait();
    }

    @FXML
    private void initialize() {
        //initialsLabel.setText("Welcome, " + userInitials);
    }
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}
