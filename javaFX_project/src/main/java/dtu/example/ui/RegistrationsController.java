package dtu.example.ui;

import dtu.example.model.Activity;
import dtu.example.model.DbContext;
import dtu.example.model.Project;
import dtu.example.model.TimeRegistration;
import dtu.example.handler.ProjectHandler;
import dtu.example.handler.TimeRegistrationHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RegistrationsController {

    @FXML
    private Label registrationTitleLabel;
    @FXML
    private TableView<TimeRegistration> registrationsTableView;
    @FXML
    private TableColumn<TimeRegistration, String> dateColumn;
    @FXML
    private TableColumn<TimeRegistration, Double> hoursColumn;
    @FXML
    private TextField hoursWorkedField;
    @FXML
    private TextField daysAgoField;
    @FXML
    private Label projectTitleLabel;

    private DbContext dbContext;
    private int currentActivityId;
    private String userInitials;
    private Runnable backHandler;
    private ProjectHandler projectHandler;


    private TimeRegistrationHandler registrationHandler;

    /** Called by MainMenuController right after FXML load */
    public void init(DbContext dbContext, String userInitials, Runnable onBack) {
        this.dbContext = dbContext;
        this.userInitials = userInitials;
        this.backHandler = onBack;
        this.registrationHandler = new TimeRegistrationHandler(dbContext);
        this.projectHandler = new ProjectHandler(dbContext);

        // wire up the table columns
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        hoursColumn.setCellValueFactory(
                new PropertyValueFactory<>("hours"));
        // … etc
    }

    public void loadFor(Activity activity) {
        registrationTitleLabel.setText("Registrations on: " + activity.getName());
    
        Project project = projectHandler.getProjectById(activity.getProjectNumber());
        if (project != null) {
            projectTitleLabel.setText("Project: " + project.getName());
        } else {
            projectTitleLabel.setText("Project: (not found)");
        }
    
        List<TimeRegistration> regs = registrationHandler.getTimeRegistrationsByUserAndActivityNumber(
                userInitials, activity.getNumber());
        currentActivityId = activity.getNumber();
        registrationsTableView.setItems(FXCollections.observableList(regs));
    }
    

    private void createRegistration() {
        try {
            double hours = Double.parseDouble(hoursWorkedField.getText());
            int daysAgo = Integer.parseInt(daysAgoField.getText());
    
            registrationHandler.registerTime(userInitials, currentActivityId, hours, daysAgo);
    
            hoursWorkedField.clear();
            daysAgoField.clear();
    
            // Optionally, refresh the table view
            loadFor(new Activity(currentActivityId, "Activity Name", 1)); // Replace with actual activity
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    @FXML
    private void onRegisterTime() {
        createRegistration();
    }

}
