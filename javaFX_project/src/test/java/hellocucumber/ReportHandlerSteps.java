package hellocucumber;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import dtu.example.handler.ProjectHandler;
import dtu.example.handler.ReportHandler;
import dtu.example.model.Activity;
import dtu.example.model.DbContext;
import dtu.example.model.Project;

public class ReportHandlerSteps {
    DbContext dbContext = new DbContext();
    ReportHandler reportHandler;
    String generatedReport;

    @Given("the report handler has been initialized")
    public void theReportHandlerHasBeenInitialized() {
        reportHandler = new ReportHandler(dbContext);
    }

    @Given("the database contains a project with number {int} and activities and activities")
    public void theDatabaseContainsAProjectWithNumberAndActivitiesAndActivities(Integer projectNumber) {
        // Add activities to the database for the given project number
        dbContext.activities.add(new Activity(1, "Design Phase", projectNumber));
        dbContext.activities.add(new Activity(2, "Development Phase", projectNumber));
        dbContext.activities.add(new Activity(3, "Testing Phase", projectNumber));
    }

    @When("I generate a report for project number {int}")
    public void iGenerateAReportForProjectNumber(Integer projectNumber) {
        generatedReport = reportHandler.getProjectReport(projectNumber);
    }

    @Then("the report contains details of all activities for project number {int}")
    public void theReportContainsDetailsOfAllActivitiesForProjectNumber(Integer projectNumber) {
        assertNotNull(generatedReport, "Generated report should not be null");
        assertTrue(generatedReport.contains("Design Phase"), "Report should contain 'Design Phase'");
        assertTrue(generatedReport.contains("Development Phase"), "Report should contain 'Development Phase'");
        assertTrue(generatedReport.contains("Testing Phase"), "Report should contain 'Testing Phase'");
    }

}
