package dtu.example.ui;

import java.util.ArrayList;
import java.util.List;

import dtu.example.model.DbContext;
import dtu.example.model.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateActivityController {
        
    private DbContext dbContext;

    @FXML private TextField titleField;
    @FXML private TextField estimatedHoursField;
    @FXML private TextField startWeekField;
    @FXML private TextField endWeekField;
    @FXML private TextField initialsField;
    @FXML private ListView<String> employeesListView;
    @FXML private Button addButton;
    @FXML private Button submitButton;

    private List<String> employees = new ArrayList<>();
    private long projectNumber;


    public void setDbContext(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public void setProjectNumber(long projectNumber) {
        this.projectNumber = projectNumber;
    }

    @FXML
    private void initialize() {
        addButton.setOnAction(e -> {
            String initials = initialsField.getText();
            if (!initials.isBlank()) {
                employees.add(initials);
                employeesListView.getItems().add(initials);
                initialsField.clear();
            }
        });
        
        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String startWeekText = startWeekField.getText();
            String endWeekText = endWeekField.getText();
            String estimatedHoursText = estimatedHoursField.getText();

        
            if (title.isBlank() || startWeekText.isBlank() || endWeekText.isBlank() || estimatedHoursText.isBlank()) {
                // Show alert if any required field is empty
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
                alert.setTitle("Missing Fields");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in title, start week, end week and estimated hours.");
                alert.showAndWait();
                return;
            }
        
            int startWeek = Integer.parseInt(startWeekText);
            int endWeek = Integer.parseInt(endWeekText);
            int estimatedHours = Integer.parseInt(estimatedHoursText);
    
            int activityNumber = dbContext.activities.size() + 1;
            long projectNumber = this.projectNumber;
            
            Activity activity = new Activity(activityNumber, title, projectNumber);
            activity.setStartWeek(startWeek);
            activity.setEndWeek(endWeek);
            activity.setEstimatedHours(estimatedHours);
    
            for (String employee : employees) {
                activity.addUserInitials(employee);
            }
    
            dbContext.activities.add(activity);
    
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        });
    }
}
