package dtu.example.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dtu.example.model.Activity;
import dtu.example.model.DbContext;
import dtu.example.model.Project;
import dtu.example.handler.ProjectHandler;
import dtu.example.handler.ActivityHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {
    DbContext dbContext;
    ProjectHandler projectHandler;
    ActivityHandler activityHandler;

    private Map<String, Long> projectNameToIdMap = new HashMap<>();

    private static String userInitials;

    @FXML
    private ListView<String> projectsListView; // Add this field and annotate it with @FXML
    @FXML
    private Label projectNameLabel;
    @FXML
    private Label projectLeadLabel;
    @FXML
    private Label projectDateLabel;

    public void setDbContext(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public void setUserInitials(String initials) {
        userInitials = initials;
    }

    public void getProject() {
        projectHandler = new ProjectHandler(dbContext);
        activityHandler = new ActivityHandler(dbContext);
        // Convert List<Project> to List<String> (e.g., project names) and populate the
        // map
        ObservableList<String> projects = FXCollections.observableArrayList();
        projectHandler.getAllProjects().forEach(project -> {
            projects.add(project.getName());
            projectNameToIdMap.put(project.getName(), project.getId()); // Map project name to ID
        });

        projectsListView.setItems(projects); // Populate the ListView

        // Add a listener to handle selection changes
        setupProjectSelectionListener();
    }

    public void getUserActivities() {
        if (activityHandler != null && userInitials != null) {
            // Fetch activities assigned to the user
            ObservableList<String> userActivities = FXCollections.observableArrayList();
            activityHandler.getAllUserActivities(userInitials).forEach(activity -> {
                userActivities.add(activity.getName()); // Add activity names to the list
            });
    
            // Populate the ListView for "Your Activities"
            yourActivitiesListView.setItems(userActivities);
        }
    }

    private void setupProjectSelectionListener() {
        // Makes it load the projects faster
        projectsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Task<Project> task = new Task<>() {
                    @Override
                    protected Project call() {
                        return getSelectedProject();
                    }
                };

                task.setOnSucceeded(event -> {
                    Project selectedProject = task.getValue();
                    if (selectedProject != null) {
                        projectNameLabel.setText(selectedProject.getName());
                        var allActivities = activityHandler.getActivitiesByProjectNumber(selectedProject.getId());
                        activities.setAll(allActivities);
                        projectLeadLabel.setText(selectedProject.getManagersAsString());
                        projectDateLabel.setText(selectedProject.getDateCreated().toString());
                        setupActivitiesTable();
                    }
                });

                task.setOnFailed(event -> {
                    System.err.println("Failed to load project: " + task.getException());
                });

                new Thread(task).start();
            }
        });
    }

    private void setupActivitiesTable() {
        // Bind the TableColumn to the name property
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        activityEmployeesColoumn.setCellValueFactory(new PropertyValueFactory<>("userInitialsAsString"));

        // Bind the ObservableList to the TableView
        activitiesTableView.setItems(activities);
    }

    public Project getSelectedProject() {
        String selectedProjectName = projectsListView.getSelectionModel().getSelectedItem();
        if (selectedProjectName != null && projectNameToIdMap.containsKey(selectedProjectName)) {
            long projectId = projectNameToIdMap.get(selectedProjectName);
            ProjectHandler projectHandler = new ProjectHandler(dbContext);
            return projectHandler.getProjectById(projectId); // Fetch the full project details
        }
        return null; // Return null if no project is selected or ID is not found
    }

    @FXML
    private void openCreateProjectPopup() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("createProject.fxml"));
        Parent popupContent = loader.load();

        CreateProjectController controller = loader.getController();
        controller.setDbContext(dbContext);

        Stage popupStage = new Stage();
        popupStage.setTitle("Create Project");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(popupContent));
        popupStage.showAndWait();

        // Refresh project list
        getProject();
    }

    @FXML
    private void openCreateActivityPopup() throws IOException {
        Parent popupContent = App.loadFXML("createActivity"); // ← reuse App's loadFXML method!

        Stage popupStage = new Stage();
        popupStage.setTitle("Create Activity");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(popupContent));
        popupStage.showAndWait();
    }

    // everything for activities
    @FXML
    private TableView<Activity> activitiesTableView; // TableView for activities
    @FXML
    private TableColumn<Activity, String> activityNameColumn; // TableColumn for activity names
    @FXML
    private TableColumn<Activity, String> activityEmployeesColoumn; // TableColumn for activity names

    private ObservableList<Activity> activities = FXCollections.observableArrayList(); // ObservableList for activities

    @FXML
    private ListView<String> yourActivitiesListView; // Add this field for "Your Activities"

    @FXML
    private void initialize() {
        // initialsLabel.setText("Welcome, " + userInitials);
    }

    public void onDbContextSet() {
        if (dbContext != null) {
            getProject();
            getUserActivities();
        }
    }

}
