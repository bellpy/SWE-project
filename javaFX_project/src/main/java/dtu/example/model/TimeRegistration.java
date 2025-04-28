package dtu.example.model;

public class TimeRegistration {
    private String userInitials;
    private int activityNumber;
    private double hoursWorked;
    private String date;

    public TimeRegistration(String userInitials, int activityNumber, double hoursWorked, String date) {
        this.userInitials = userInitials;
        this.activityNumber = activityNumber;
        this.hoursWorked = hoursWorked;
        this.date = date;
    }
}
