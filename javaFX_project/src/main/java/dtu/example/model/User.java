package dtu.example.model;

public class User {
    public String initials;
    private int numberOfAssignedActivities;
    private static final int MAX_CAPACITY = 20;

    public User(String initials) {
        this.initials = initials;
        this.numberOfAssignedActivities = 0;
    }

    public int calculateUserCapacity() {
        return MAX_CAPACITY - numberOfAssignedActivities;
    }

    public String getInitials() {
        return initials;
    }

    public void assignActivity() {
        numberOfAssignedActivities++;
    }

    public int getNumberOfAssignedActivities() {
        return numberOfAssignedActivities;
    }
}
