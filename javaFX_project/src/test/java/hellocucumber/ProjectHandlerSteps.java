package hellocucumber;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import dtu.example.handler.ProjectHandler;
import dtu.example.model.DbContext;
import dtu.example.model.Project;

public class ProjectHandlerSteps {

    DbContext dbContext = new DbContext();
    ProjectHandler projectHandler;


    // Scenario 1: User can create a new project
    @Given("the project handler have been initilized")
    public void theProjectHandlerHaveBeenInitilized() {
        projectHandler = new ProjectHandler(dbContext);
    }

    @When("I create a new project with name {string}")
    public void iCreateANewProjectWithName(String string) {
        projectHandler.createProject(string, null);
    }

    @Then("the dbContext contains the project {string}")
    public void theDbContextContainsTheProject(String string) {
        Project createdProject = dbContext.projects.stream()
                .filter(p -> p.name.equals(string))
                .findFirst().get();
        assertNotNull(createdProject);
    }
}
