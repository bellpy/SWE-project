package hellocucumber;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

import java.util.List;

import dtu.example.handler.ActivityHandler;
import dtu.example.handler.TimeRegistrationHandler;
import dtu.example.model.DbContext;
import dtu.example.model.TimeRegistration;
import dtu.example.model.Activity;
import dtu.example.model.User;

public class TimeRegistrationSteps {
    DbContext dbContext;
    TimeRegistrationHandler timeRegistrationHandler;

    String userInitials;
    int activityNumber;

    @Given("the time registrations handler has been initialized")
    public void theTimeRegistrationsHandlerHasBeenInitialized() {
        dbContext = new DbContext();
        timeRegistrationHandler = new TimeRegistrationHandler(dbContext);
    }

    @Given("a user with initials {string} exists and an activity with number {int} exists")
    public void aUserWithInitialsExistsAndAnActivityWithNumberExists(String string, Integer int1) {
        dbContext.activities.add(new Activity(int1, "Activity " + int1, 0));
        dbContext.users.add(new User(string));
        userInitials = string;
        activityNumber = int1;
    }

    @Given("a user with initials {string} exists and activity with number {int} exists")
    public void aUserWithInitialsExistsAndActivityWithNumberExists(String string, Integer int1) {
        dbContext.activities.add(new Activity(int1, "Activity " + int1, 0));
        dbContext.users.add(new User(string));
        userInitials = string;
        activityNumber = int1;
        // real
    }

    @When("user creates a new time registration for activity {int} from {int} days ago")
    public void userCreatesANewTimeRegistrationForActivityFromDaysAgo(Integer int1, Integer int2) {
        // Register time for the user
        timeRegistrationHandler.registerTime(userInitials, activityNumber, 8, int2);
    }

    @When("user creates a new time registration for activity {int} with hours {int}")
    public void userCreatesANewTimeRegistrationForActivityWithHours(Integer int1, Integer int2) {
        timeRegistrationHandler.registerTime(userInitials, activityNumber, 8, int2);
        // real
    }

    @Then("get time registration returns for user {string} and activity {int} the current date minus {int} days")
    public void getTimeRegistrationReturnsForUserAndActivityTheCurrentDateMinusDays(String string, Integer int1,
            Integer int2) {
        List<TimeRegistration> allRegistrations = timeRegistrationHandler.getTimeRegistrationsByUserAndActivityNumber(string, activityNumber);
        TimeRegistration timeRegistration = allRegistrations.stream()
                .filter(tr -> tr.getUserInitials().equals(string) && tr.getActivityNumber() == activityNumber)
                .findFirst()
                .orElse(null);
        assertNotNull(timeRegistration, "Time registration should exist for user and activity");
        LocalDate expectedDate = LocalDate.now().minusDays(int2);
        assertEquals(expectedDate, timeRegistration.getDate(), "Time registration date should match expected date");
    }

    @Then("the dbContext contains the time registration for activity {int} with hours {int}")
    public void theDbContextContainsTheTimeRegistrationForActivityWithHours(Integer int1, Integer int2) {
        TimeRegistration timeRegistration = dbContext.timeRegistrations.stream()
                .filter(tr -> tr.getActivityNumber() == int1 && tr.getHours() == int2)
                .findFirst()
                .orElse(null);
        assertNotNull(timeRegistration, "Time registration should exist in dbContext");
    }

}
