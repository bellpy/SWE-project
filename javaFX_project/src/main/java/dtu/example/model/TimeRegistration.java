package dtu.example.model;

import java.time.LocalDate;
import java.util.Date;

public class TimeRegistration {
    private String userInitials;
    private int activityNumber;
    private double hoursWorked;
    private LocalDate date;

    public TimeRegistration(String userInitials, int activityNumber, double hoursWorked, LocalDate date) {
        this.userInitials = userInitials;
        this.activityNumber = activityNumber;
        this.hoursWorked = hoursWorked;
        this.date = date;
    }

    public String getUserInitials() {
        return this.userInitials;
    }

    public int getActivityNumber() {
        return this.activityNumber;
    }

    public double getHours() {
        return this.hoursWorked;
    }

    public LocalDate getDate() {
        return this.date;
    }

}
