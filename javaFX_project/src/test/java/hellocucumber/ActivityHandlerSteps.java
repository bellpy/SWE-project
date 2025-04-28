package hellocucumber;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import dtu.example.handler.ActivityHandler;
import dtu.example.model.DbContext;
import dtu.example.model.Activity;

public class ActivityHandlerSteps {
    DbContext dbContext = new DbContext();
    ActivityHandler activityHandler;
    Activity activity;

    @Given("the activity handler have been initilized")
    public void theActivityHandlerHaveBeenInitilized() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("they create a new activity with {string}")
    public void theyCreateANewActivityWith(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the dbContext activity is created")
    public void theDbContextActivityIsCreated() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}