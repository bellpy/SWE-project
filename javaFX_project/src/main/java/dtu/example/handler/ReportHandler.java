package dtu.example.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dtu.example.handler.interfaces.IActivityHandler;
import dtu.example.handler.interfaces.ITimeRegistrationHandler;
import dtu.example.model.Activity;

public class ReportHandler implements dtu.example.handler.interfaces.IReportHandler{
    private final IActivityHandler activityHandler;
    private final ITimeRegistrationHandler timeRegistrationHandler;

    private String filePath;

    public ReportHandler(IActivityHandler activityHandler, ITimeRegistrationHandler timeRegistrationHandler) {
        this.activityHandler = activityHandler;
        this.timeRegistrationHandler = timeRegistrationHandler;
    }

    public String generateAndSaveReport(long projectNumber) {
        String report = getProjectReport(projectNumber);
        saveReportToFile(report, projectNumber);
        return filePath;
    }

    public String getProjectReport(long projectNumber) {
        StringBuilder report = new StringBuilder();
        List<Activity> activities = activityHandler.getActivitiesByProjectNumber(projectNumber); // Use ActivityHandler

        activities.forEach(activity -> {
            double registeredHours = timeRegistrationHandler.getTotalRegisteredHoursByActivity(activity.getNumber()); // Use
                                                                                                                      // TimeRegistrationHandler

            report.append("Activity Number: ").append(activity.getNumber()).append("\n")
                    .append("Activity Name: ").append(activity.getName()).append("\n")
                    .append("Assigned Users: ").append(activity.getUserInitials()).append("\n")
                    .append("Estimated Hours: ").append(activity.getEstimatedHours()).append("\n")
                    .append("Registered Hours: ").append(registeredHours).append("\n\n");
        });

        return report.toString();
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
