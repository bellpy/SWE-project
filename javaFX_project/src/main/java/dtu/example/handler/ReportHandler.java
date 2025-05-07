package dtu.example.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dtu.example.model.DbContext;

public class ReportHandler {
    DbContext dbContext;

    private String filePath;

    public ReportHandler(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public String generateAndSaveReport(long projectNumber) {
        String report = getProjectReport(projectNumber);
        saveReportToFile(report, projectNumber);
        return filePath;
    }

    public String getProjectReport(long projectNumber) {
        StringBuilder report = new StringBuilder();
        dbContext.activities.stream()
                .filter(activity -> activity.getProjectNumber() == projectNumber)
                .forEach(activity -> {
                    report.append("Activity Number: ").append(activity.getNumber()).append("\n")
                          .append("Activity Name: ").append(activity.getName()).append("\n")
                          .append("Assigned Users: ").append(activity.getUserInitials()).append("\n\n");
                });
        var reportString = report.toString();
        return reportString;
    }

    public void saveReportToFile(String report, long projectNumber) {
        // Generate a timestamp for the file name
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "ProjectReport_" + projectNumber + "_" + timestamp + ".txt";

        // Define the file path outside the main project directory
        filePath = System.getProperty("user.home") + File.separator + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(report);
            System.out.println("Report saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving report to file: " + e.getMessage());
        }
    }
}
