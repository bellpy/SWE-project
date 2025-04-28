package hellocucumber;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import dtu.example.handler.ActivityHandler;
import dtu.example.model.DbContext;
import dtu.example.model.Activity;

public class ActivityHandlerSteps {
    DbContext dbContext = new DbContext();
    ActivityHandler activityHandler;

    @Given("the activity handler have been initilized")
    public void theActivityHandlerHaveBeenInitilized() {
        activityHandler = new ActivityHandler(dbContext);
    }

    @When("they create a new activity with {string}")
    public void theyCreateANewActivityWith(String string) {
        activityHandler.createActivity(0, string, 0);
    }

    @Then("the dbContext activity is created")
    public void theDbContextActivityIsCreated(String string) {
        Activity activity = dbContext.activities.stream()
        .filter(a -> a.getName().equals(string))
        .findFirst()
        .orElse(null);
    
        // Verify the activity exists
        assertNotNull(activity, "Activity with name '" + string + "' should exist in dbContext");
    
        // Verify it has the correct properties (assuming projectNumber 0 was used in @When)
        assertEquals(0, activity.getProjectNumber(), "Activity should belong to correct project");
        assertEquals(string, activity.getName(), "Activity name should match");
    }
}