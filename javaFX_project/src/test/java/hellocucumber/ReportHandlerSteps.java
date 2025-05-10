package hellocucumber;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import dtu.example.handler.ProjectHandler;
import dtu.example.handler.ReportHandler;
import dtu.example.model.Activity;
import dtu.example.model.DbContext;
import dtu.example.model.Project;

public class ReportHandlerSteps {
    DbContext dbContext = new DbContext();
    ReportHandler reportHandler;
    String generatedReport;

    String filePath;
    File reportFile;

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

    @When("I save a report with content {string} for project number {int}")
    public void iSaveAReportWithContentForProjectNumber(String content, Integer projectNumber) {
        reportHandler.saveReportToFile(content, projectNumber);
        filePath = System.getProperty("user.home") + File.separator + "ProjectReport_" + projectNumber + "_";
    }

    @Then("a file is created with the report content")
    public void aFileIsCreatedWithTheReportContent() throws IOException {
        // Find the file with the correct prefix
        File dir = new File(System.getProperty("user.home"));
        File[] matchingFiles = dir.listFiles((d, name) -> name.startsWith("ProjectReport_1_") && name.endsWith(".txt"));

        assertNotNull(matchingFiles, "No file was created");
        assertTrue(matchingFiles.length > 0, "No matching file was found");

        // Find the most recently created file
        File reportFile = null;
        long lastModifiedTime = Long.MIN_VALUE;
        for (File file : matchingFiles) {
            if (file.lastModified() > lastModifiedTime) {
                lastModifiedTime = file.lastModified();
                reportFile = file;
            }
        }

        assertNotNull(reportFile, "Failed to find the most recently created file");

        // Verify the content of the file
        String fileContent = Files.readString(Paths.get(reportFile.getAbsolutePath()));
        assertEquals("Test Report Content", fileContent);

        // Clean up the file after the test
        assertTrue(reportFile.delete(), "Failed to delete the test file");
    }

    @When("I generate and save a report for project number {int}")
    public void iGenerateAndSaveAReportForProjectNumber(Integer projectNumber) {
        String filePath = reportHandler.generateAndSaveReport(projectNumber);
        reportFile = new File(filePath); // Store the generated file reference
    }

    @Then("a file is created with the generated report content")
    public void aFileIsCreatedWithTheGeneratedReportContent() throws IOException {
        assertNotNull(reportFile, "Report file was not created");
        assertTrue(reportFile.exists(), "Report file does not exist");

        String fileContent = Files.readString(Paths.get(reportFile.getAbsolutePath()));
        assertTrue(fileContent.contains("Activity Number: 1"), "Report content is missing 'Activity Number: 1'");
        assertTrue(fileContent.contains("Activity Name: Design Phase"), "Report content is missing 'Activity Name: Design Phase'");

        // Clean up the file after the test
        assertTrue(reportFile.delete(), "Failed to delete the test file");
    }

}
