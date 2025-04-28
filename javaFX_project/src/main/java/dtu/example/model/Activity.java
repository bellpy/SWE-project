package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class Activity {
    private int projectNumber;
    private String name;
    private int number;
    private List<String> userInitials = new ArrayList<>();

    public Activity(int activityNumber, String name, int projectNumber) {
        this.number = activityNumber;
        this.name = name;
        this.projectNumber = projectNumber;
        this.userInitials = new ArrayList<>();
    }

    // Getters and setters
    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getUserInitials() {
        return userInitials;
    }

    public void setUserInitials(List<String> userInitials) {
        this.userInitials = userInitials;
    }
}