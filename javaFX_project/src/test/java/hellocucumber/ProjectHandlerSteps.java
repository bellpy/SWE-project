package hellocucumber;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import dtu.example.handler.ProjectHandler;
import dtu.example.model.DbContext;
import dtu.example.model.Project;

public class ProjectHandlerSteps {

    DbContext dbContext = new DbContext();
    ProjectHandler projectHandler;
    Project retrievedProject;
    long projectId;

    // Scenario 1: User can create a new project
    @Given("the project handler has been initialized")
    public void theProjectHandlerHaveBeenInitilized() {
        projectHandler = new ProjectHandler(dbContext);
    }

    @When("I create a new project with name {string}")
    public void iCreateANewProjectWithName(String string) {
        Project project = projectHandler.createProject(string, null);
        projectId = project.getId();
    }

    @Then("the dbContext contains the project {string}")
    public void theDbContextContainsTheProject(String string) {
        Project createdProject = dbContext.projects.stream()
                .filter(p -> p.getName().equals(string))
                .findFirst().get();
        assertNotNull(createdProject);
    }

    @When("I retrieve the project by its ID")
    public void iRetrieveTheProjectByItsID() {
        retrievedProject = projectHandler.getProjectById(projectId);
    }

    @Then("the retrieved project has the name {string}")
    public void theRetrievedProjectHasTheName(String name) {
        assertNotNull(retrievedProject);
        assertEquals(name, retrievedProject.getName());
    }

    @When("I retrieve a project by ID {int}")
    public void iRetrieveAProjectByID(Integer id) {
        retrievedProject = projectHandler.getProjectById(id);
    }

    @Then("no project is retrieved")
    public void noProjectIsRetrieved() {
        assertNull(retrievedProject);
    }

    @Given("I create a new project with name {string} with manager {string}")
    public void iCreateANewProjectWithNameWithManager(String string, String string2) {
        List<String> managerInitials = List.of(string2);
        Project project = projectHandler.createProject(string, managerInitials);
        projectId = project.getId();
    }

    @Then("the project details has {string}")
    public void theProjectDetailsHas(String string) {
        String projectDetails = projectHandler.getProjectDetailsById(projectId);
        assertNotNull(projectDetails, "Project details should not be null");
        assertTrue(projectDetails.contains(string), "Project details should contain: " + string);
    }

    @When("I manage projects by adding {string} and removing its ID")
    public void iManageProjectsByAddingAndRemovingItsID(String projectName) {
        Project projectToAdd = new Project(projectName, projectId);
        boolean result = projectHandler.manageProjects(projectToAdd, (int) projectId);
        assertTrue(result, "The project should be added and removed successfully.");
    }

    @Then("the project {string} is added and removed successfully")
    public void theProjectIsAddedAndRemovedSuccessfully(String projectName) {
        Project project = dbContext.projects.stream()
                .filter(p -> p.getName().equals(projectName))
                .findFirst()
                .orElse(null);
        assertNotNull(project, "The project should no longer exist in the database.");
    }

}
