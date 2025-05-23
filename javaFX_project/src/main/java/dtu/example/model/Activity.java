package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class Activity {
    private static int nextNumber = 1;

    private List<String> userInitials;
    private long projectNumber;
    private String activityName;
    private int number;
    private int startWeek;
    private int endWeek;
    private int estimatedHours;

    public Activity(int number, String name, long projectNumber) {
        //Constructor used for tests
        if(number > nextNumber) {
            //To ensure that the next number is always greater than the last used number
            nextNumber = number +2;
        }

        this.number = number;
        this.activityName = name;
        this.projectNumber = projectNumber;
        this.userInitials = new ArrayList<>();
    }

    public Activity(String name, long projectNumber) {
        this.number = nextNumber++; 
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
    
    public List<String> getUserInitials() {
        return this.userInitials;
    }
    
    public void addUserInitials(String userInitials) {
        this.userInitials.add(userInitials);
    }

    public void setAllUserInitials(List<String> userInitials) {
        this.userInitials = userInitials;
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