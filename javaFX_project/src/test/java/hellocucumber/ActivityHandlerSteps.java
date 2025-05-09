package hellocucumber;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
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
    User user = new User("JS");

    @Given("the activity handler have been initialized")
    public void theActivityHandlerHaveBeenInitialized() {
        activityHandler = new ActivityHandler(dbContext);
    }

    @When("they create a new activity with {string}")
    public void theyCreateANewActivityWith(String string) {
        // Define startWeek, endWeek, estimatedHours, and userInitials
        List<String> userInitials = Arrays.asList(user.getInitials()); // Example initials for the user creating the
                                                                       // activity

        // Pass all required parameters
        activityHandler.createActivity(0, string, 0, 0, 0, userInitials);
    }

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

    @When("user retrieves their assigned activities")
    public void userRetrievesTheirAssignedActivities() {
        activities = activityHandler.getAllUserActivities(user.getInitials());
    }

    @Given("is assigned an activity with name {string}")
    public void isAssignedAnActivityWithName(String string) {
        List<String> userInitials = Arrays.asList(user.getInitials());
        int activityNumber = activityHandler.createActivity(0, string, 0, 0, 0, userInitials);
        activityHandler.assignUserToActivity(activityNumber, userInitials.get(0));
    }

    @Then("the activity with name {string} is returned")
    public void theActivityWithNameIsReturned(String string) {
        Activity usersActivity = activities.stream()
                .filter(activity -> activity.getName().equals(string))
                .findFirst()
                .orElse(null);

        assertNotNull(usersActivity, "Activity with name '" + string + "' should exist in the list");
        assertEquals(string, usersActivity.getName(), "Activity name should match");
    }

    @Given("an activity with name {string} exists")
    public void anActivityWithNameExists(String name) {
        List<String> userInitials = Arrays.asList(user.getInitials());
        int activityNumber = activityHandler.createActivity(0, name, 0, 0, 0, userInitials);
        activity = dbContext.activities.stream()
                .filter(a -> a.getNumber() == activityNumber)
                .findFirst()
                .orElse(null);
        assertNotNull(activity, "Activity should be created successfully");
    }

    @When("the activity is updated with name {string}")
    public void theActivityIsUpdatedWithName(String updatedName) {
        assertNotNull(activity, "Activity must exist before updating");
        activity.setName(updatedName);
        activityHandler.updateActivity(activity);
    }

    @Given("users {string} are assigned to the activity")
    public void usersAreAssignedToTheActivity(String userInitialsString) {
        assertNotNull(activity, "Activity must exist before assigning users");
        String[] userInitialsArray = userInitialsString.split(", ");
        for (String initials : userInitialsArray) {
            activityHandler.assignUserToActivity(activity.getNumber(), initials);
        }
    }

    @When("users are retrieved by activity number")
    public void usersAreRetrievedByActivityNumber() {
        assertNotNull(activity, "Activity must exist before retrieving users");
        List<String> retrievedUsers = activityHandler.getUsersByActivityNumber(activity.getNumber());
        assertNotNull(retrievedUsers, "Retrieved users list should not be null");
        userInitials = String.join(", ", retrievedUsers);
    }

    @Then("the users {string} are returned")
public void theUsersAreReturned(String expectedUsers) {
    List<String> expectedUserList = Arrays.asList(expectedUsers.split(", "));
    List<String> actualUserList = Arrays.asList(userInitials.split(", "));
    assertEquals(expectedUserList.size(), actualUserList.size(), "User count should match");
    assertTrue(actualUserList.containsAll(expectedUserList), "Retrieved users should match the expected users");
}

@Then("the activity it is renamed in the database to {string}")
public void theActivityItIsRenamedInTheDatabaseTo(String string) {
    Activity updatedActivity = dbContext.activities.stream()
            .filter(a -> a.getNumber() == activity.getNumber())
            .findFirst()
            .orElse(null);
    assertNotNull(updatedActivity, "Updated activity should exist in the database");
    assertEquals(string, updatedActivity.getName(), "Activity name should match the updated name");
}
}
