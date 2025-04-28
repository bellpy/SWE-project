package dtu.example.ui;

import java.io.IOException;

import dtu.example.model.DbContext;
import dtu.example.model.Project;
import dtu.example.handler.ProjectHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {
    DbContext dbContext;

    // @FXML
    // private Label initialsLabel;

    // private static String userInitials;

    // public static void setUserInitials(String initials) {
    // userInitials = initials;
    // }
    public void setDbContext(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    private static String userInitials;
    
    @FXML
    private ListView<String> projectsListView; // Add this field and annotate it with @FXML
   
    public void setUserInitials(String initials) {
        userInitials = initials;
    }

    public void GetProject() {
        ProjectHandler projectHandler = new ProjectHandler(dbContext);

        // Convert List<Project> to List<String> (e.g., project names)
        ObservableList<String> projects = FXCollections.observableArrayList(
                projectHandler.getAllProjects().stream()
                        .map(Project::getName) // Assuming Project has a getName() method
                        .toList());

        projectsListView.setItems(projects); // Populate the ListView
    }

    public void onDbContextSet() {
        if (dbContext != null) {
            GetProject();
        }
    }

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
        // initialsLabel.setText("Welcome, " + userInitials);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}
