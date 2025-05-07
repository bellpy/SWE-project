package dtu.example.ui;

import dtu.example.model.Activity;
import dtu.example.model.DbContext;
import dtu.example.model.TimeRegistration;
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
    // … and your editColumn (with a cellFactory) …

    private DbContext dbContext;
    private String userInitials;
    private Runnable backHandler;

    private TimeRegistrationHandler registrationHandler;

    /** Called by MainMenuController right after FXML load */
    public void init(DbContext dbContext, String userInitials, Runnable onBack) {
        this.dbContext = dbContext;
        this.userInitials = userInitials;
        this.backHandler = onBack;
        this.registrationHandler = new TimeRegistrationHandler(dbContext);

        // wire up the table columns
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        hoursColumn.setCellValueFactory(
                new PropertyValueFactory<>("hours"));
        // … etc
    }

    /** Populate UI for the given activity */
    public void loadFor(Activity activity) {
        registrationTitleLabel.setText("Registrations on: " + activity.getName());
        List<TimeRegistration> regs = registrationHandler.getTimeRegistrationsByUserAndActivityNumber(userInitials,
                activity.getNumber());
        registrationsTableView.setItems(
                FXCollections.observableList(regs));
    }

    private void createRegistration() {
        try {
            // Parse input values
            double hours = Double.parseDouble(hoursWorkedField.getText());
            int daysAgo = Integer.parseInt(daysAgoField.getText());
    
            // Use the activity ID (replace with actual logic to get the activity ID)
            var activityId = 1; // Replace with actual activity ID from activity
    
            // Create the time registration
            registrationHandler.registerTime(userInitials, activityId, hours, daysAgo);
    
            // Clear the input fields after successful registration
            hoursWorkedField.clear();
            daysAgoField.clear();
    
            // Optionally, refresh the table view
            loadFor(new Activity(activityId, "Activity Name", 1)); // Replace with actual activity
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + e.getMessage());
            // Optionally, show an error message to the user
        }
    }

    @FXML
    private void onRegisterTime() {
        createRegistration();
    }

}
