package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class Activity {
    private List<String> userInitials;
    private int projectNumber;
    private String activityName;
    private int number;

    public Activity(int number, String name, int projectNumber) {
        this.number = number;
        this.activityName = name;
        this.projectNumber = projectNumber;
        this.userInitials = new ArrayList<>();
    }

    public String getName() {
        return this.activityName;
    }

    public int getProjectNumber() {
        return this.projectNumber;
    }

    public List<String> getUserInitials() {
        return this.userInitials;
    }

    public int getNumber() {
        return this.number;
    }
}