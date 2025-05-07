package hellocucumber;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import dtu.example.handler.ActivityHandler;
import dtu.example.model.DbContext;
import dtu.example.model.Activity;
import dtu.example.model.User;

public class ActivityHandlerSteps {
    DbContext dbContext = new DbContext();
    ActivityHandler activityHandler;
    Activity activity;

    String userInitials;
    List<Activity> activities;

    // Scenario 1
    @Given("the activity handler have been initilized")
    public void theActivityHandlerHaveBeenInitilized() {
        activityHandler = new ActivityHandler(dbContext);
    }

    @When("they create a new activity with {string}")
    public void theyCreateANewActivityWith(String string) {
        activityHandler.createActivity(0, string, 0);
    }

    @Then("the dbContext activity is created with name {string}")
    public void theDbContextActivityIsCreatedWithName(String string) {
        Activity activity = dbContext.activities.stream()
                .filter(a -> a.getName().equals(string))
                .findFirst()
                .orElse(null);

        // Verify the activity exists
        assertNotNull(activity, "Activity with name '" + string + "' should exist in dbContext");

        // Verify it has the correct properties (assuming projectNumber 0 was used in
        // @When)
        assertEquals(0, activity.getProjectNumber(), "Activity should belong to correct project");
        assertEquals(string, activity.getName(), "Activity name should match");
    }

    // Scenario 2
    @Given("a user with initials {string} exists")
    public void aUserWithInitialsExists(String string) {
        User user = new User(string);
        dbContext.users.add(user);
    }

    @Given("an employee with initials {string} exists")
    public void anEmployeeWithInitialsExists(String string) {
        User employee = new User(string);
        dbContext.users.add(employee);
        userInitials = string;
    }

    @Given("an activity with number {int} exists")
    public void anActivityWithNumberExists(Integer activityNumber) {
        activity = new Activity(activityNumber, "Test Activity", 1001);
        dbContext.activities.add(activity);
    }

    @When("user adds employee with initials {string} to the activity")
    public void userAddsEmployeeWithInitialsToTheActivity(String initials) {
        activity.addUserInitials(initials);
    }

    @Then("the activity {int} has the employee with initials {string}, assigned to it")
    public void theActivityHasTheEmployeeWithInitialsAssignedTo(Integer activityNumber, String expectedInitials) {
        Activity activity = dbContext.activities.stream()
                .filter(a -> a.getNumber() == activityNumber)
                .findFirst()
                .orElse(null);

        assertNotNull(activity, "Activity should exist");
        assertEquals(activityNumber, activity.getNumber(), "Activity number should match");
        assertTrue(activity.getUserInitials().contains(expectedInitials),
                "Activity should have employee with initials " + expectedInitials);
    }

    @Given("is assigned an activity with number {int}")
    public void isAssignedAnActivityWithNumber(Integer int1) {
        activityHandler.createActivity(0, userInitials, int1);
        activityHandler.assignUserToActivity(int1, userInitials);
    }

    @When("user retrieves their assigned activities")
    public void userRetrievesTheirAssignedActivities() {
        activities = activityHandler.getAllUserActivities(userInitials);
    }

    @Then("the activity {int} is returned")
    public void theActivityIsReturned(Integer int1) {
        assertNotNull(activities, "Activities list should not be null");
        assertFalse(activities.isEmpty(), "Activities list should not be empty");

        boolean activityFound = activities.stream()
                .anyMatch(activity -> activity.getNumber() == int1);

        assertTrue(activityFound, "Activity with number " + int1 + " should be in the list");
    }
}