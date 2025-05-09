package dtu.example.ui;

import dtu.example.handler.ProjectHandler;
import dtu.example.model.DbContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CreateProjectController {

    private ProjectHandler projectHandler;

    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private TextField initialsField;
    @FXML private ListView<String> managerListView;
    @FXML private Button addButton;
    @FXML private Button submitButton;

    private List<String> managers = new ArrayList<>();

    public void setDbContext(DbContext dbContext) {
        this.projectHandler = new ProjectHandler(dbContext);
    }

    @FXML
    private void initialize() {
        addButton.setOnAction(e -> {
            String initials = initialsField.getText();
            if (!initials.isBlank()) {
                managers.add(initials);
                managerListView.getItems().add(initials);
                initialsField.clear();
            }
        });

        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            if (!title.isBlank()) {
                projectHandler.createProject(title, managers);

                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            }
        });
    }
}
