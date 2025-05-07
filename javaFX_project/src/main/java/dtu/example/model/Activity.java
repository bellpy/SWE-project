package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class Activity {
    private List<String> userInitials;
    private long projectNumber;
    private String activityName;
    private int number;
    private int startWeek;
    private int endWeek;
    private int estimatedHours;

    public Activity(int number, String name, long projectNumber) {
        this.number = number;
        this.activityName = name;
        this.projectNumber = projectNumber;
        this.userInitials = new ArrayList<>();
    }

    public String getName() {
        return this.activityName;
    }

    public void setName(String name) {
        this.activityName = name;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }
    
    public int getEstimatedHours() {
        return this.estimatedHours;
    }

    public long getProjectNumber() {
        return this.projectNumber;
    }

    public void setProjectNumber(long projectNumber) {
        this.projectNumber = projectNumber;
    }
    
    public List<String> getUserInitials() {
        return this.userInitials;
    }
    
    public void addUserInitials(String userInitials) {
        this.userInitials.add(userInitials);
    }

    public int getNumber() {
        return this.number;
    }

    public String getUserInitialsAsString() {
        return String.join(", ", this.userInitials); // Convert list to comma-separated string
    }
    
    public void removeUserInitial(String userInitial) {
        this.userInitials.remove(userInitial);
    }

    public int getStartWeek() {
        return startWeek;
    }
    
    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }
    
    public int getEndWeek() {
        return endWeek;
    }
    
    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }
}