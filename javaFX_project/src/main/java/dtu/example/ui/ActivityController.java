package dtu.example.ui;

import java.util.ArrayList;
import java.util.List;

import dtu.example.model.DbContext;
import dtu.example.handler.ActivityHandler;
import dtu.example.handler.interfaces.IActivityHandler;
import dtu.example.model.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ActivityController {
        
    private IActivityHandler activityHandler;

    @FXML private TextField titleField;
    @FXML private TextField estimatedHoursField;
    @FXML private TextField startWeekField;
    @FXML private TextField endWeekField;
    @FXML private TextField initialsField;
    @FXML private ListView<String> employeesListView;
    @FXML private Button addButton;
    @FXML private Button submitButton;
    @FXML private Label activityTitleLabel;

    private List<String> employees = new ArrayList<>();
    private long projectNumber;
    private Activity existingActivity = null;  // If not null, we're in edit mode
    private boolean isEditMode = false;

    public void setHandler(IActivityHandler activityHandler) {
        this.activityHandler = activityHandler;
    }

    public void setProjectNumber(long projectNumber) {
        this.projectNumber = projectNumber;
    }

    @FXML
    private void initialize() {
        employeesListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedInitial = employeesListView.getSelectionModel().getSelectedItem();
                if (selectedInitial != null && isEditMode && existingActivity != null) {
                    existingActivity.removeUserInitial(selectedInitial);
                    employees.remove(selectedInitial);
                    employeesListView.getItems().remove(selectedInitial);
                }
            }
        });

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
        
            if (isEditMode && existingActivity != null) {
                // Modify the existing activity
                Activity updatedActivity = new Activity(existingActivity.getNumber(), title, projectNumber);
                updatedActivity.setStartWeek(startWeek);
                updatedActivity.setEndWeek(endWeek);
                updatedActivity.setEstimatedHours(estimatedHours);
                updatedActivity.setAllUserInitials(employees);

                activityHandler.updateActivity(updatedActivity); 
            } else {
                // Add new activity
                activityHandler.createActivity(projectNumber, title, startWeek, endWeek, estimatedHours, employees); // Use ActivityHandler
            }
        
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        });
        
        
    }

    public void setActivityToEdit(Activity activity) {
        this.existingActivity = activity;
        this.isEditMode = true;
        fillFieldsForEdit();
        activityTitleLabel.setText("Edit Activity");
    }
    
    private void fillFieldsForEdit() {
        titleField.setText(existingActivity.getName());
        startWeekField.setText(String.valueOf(existingActivity.getStartWeek()));
        endWeekField.setText(String.valueOf(existingActivity.getEndWeek()));
        estimatedHoursField.setText(String.valueOf(existingActivity.getEstimatedHours()));

        employees.clear();
        employees.addAll(existingActivity.getUserInitials());
    
        employeesListView.getItems().clear();
        employeesListView.getItems().addAll(employees);
    }   
}
