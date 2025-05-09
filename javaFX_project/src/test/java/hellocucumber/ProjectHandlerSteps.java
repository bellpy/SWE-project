package hellocucumber;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

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
}
