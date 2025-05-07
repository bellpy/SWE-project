package dtu.example.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dtu.example.model.Activity;
import dtu.example.model.DbContext;
import dtu.example.model.Project;
import dtu.example.handler.ProjectHandler;
import dtu.example.handler.ReportHandler;
import dtu.example.handler.ActivityHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {
    DbContext dbContext;
    ProjectHandler projectHandler;
    ActivityHandler activityHandler;
    ReportHandler reportHandler;

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
        reportHandler = new ReportHandler(dbContext);
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
                        // Update the project details
                        projectNameLabel.setText(selectedProject.getName());
                        var allActivities = activityHandler.getActivitiesByProjectNumber(selectedProject.getId());
                        activities.setAll(allActivities);
                        projectLeadLabel.setText(selectedProject.getManagersAsString());
                        projectDateLabel.setText(selectedProject.getDateCreated().toString());
                        setupActivitiesTable();
    
                        // Show the project details pane
                        showProjectDetails();
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
        Project selectedProject = getSelectedProject();

        if (selectedProject == null) {
            // Show alert box
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("No Project Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a project before creating an activity.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(App.class.getResource("createActivity.fxml"));
        Parent popupContent = loader.load();

        CreateActivityController controller = loader.getController();
        controller.setDbContext(dbContext);
        controller.setProjectNumber(selectedProject.getId());

        Stage popupStage = new Stage();
        popupStage.setTitle("Create Activity");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(popupContent));
        popupStage.showAndWait();

        refreshActivities();
    }

    public void refreshActivities() {
        Project selectedProject = getSelectedProject();
        if (selectedProject != null) {
            var updatedActivities = activityHandler.getActivitiesByProjectNumber(selectedProject.getId());
            activities.setAll(updatedActivities);
        }
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
        preloadRegistrationsView();
        setupActivitySelectionListener();
        setupProjectSelectionListener();
        // initialsLabel.setText("Welcome, " + userInitials);
    }

    public void onDbContextSet() {
        if (dbContext != null) {
            getProject();
            getUserActivities();
            preloadRegistrationsView();
        }
    }

    @FXML
    private StackPane detailsStackPane; // The StackPane containing the project and activity details
    @FXML
    private AnchorPane projectDetailsPane; // The default project details pane

    private Parent registrationsView; // Cache the RegistrationsView for reuse

    private void setupActivitySelectionListener() {
        yourActivitiesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Activity selectedActivity = activityHandler.getAllUserActivities(userInitials)
                    .stream()
                    .filter(activity -> activity.getName().equals(newValue))
                    .findFirst()
                    .orElse(null);
    
                if (selectedActivity != null) {
                    try {
                        // Ensure the RegistrationsView is preloaded
                        if (registrationsView == null) {
                            preloadRegistrationsView();
                        }
    
                        // Retrieve the controller from the UserData
                        RegistrationsController controller = (RegistrationsController) registrationsView.getUserData();
                        if (controller != null) {
                            // Update the RegistrationsView with the selected activity
                            controller.loadFor(selectedActivity);
    
                            // Show the RegistrationsView
                            detailsStackPane.getChildren().setAll(registrationsView);
                        } else {
                            throw new IllegalStateException("RegistrationsController is not set in UserData.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void preloadRegistrationsView() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("RegistrationsView.fxml"));
            registrationsView = loader.load();
    
            // Get the controller and store it in the UserData of the view
            RegistrationsController controller = loader.getController();
            controller.init(dbContext, userInitials, this::showProjectDetails);
            registrationsView.setUserData(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateReport() {
        // Get the selected project (assuming you have a way to get it)
        Project selectedProject = getSelectedProject();

        if (selectedProject == null) {
            // Show an alert if no project is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Project Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a project to generate a report.");
            alert.showAndWait();
            return;
        }

        // Generate and save the report
        String filePath = reportHandler.generateAndSaveReport(selectedProject.getId());

        // Show the file path in an alert box
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Report Generated");
        alert.setHeaderText(null);
        alert.setContentText("The report has been saved to:\n" + filePath);
        alert.showAndWait();
    }

    private void showProjectDetails() {
        // Show the project details pane
        detailsStackPane.getChildren().setAll(projectDetailsPane);
    }

}
