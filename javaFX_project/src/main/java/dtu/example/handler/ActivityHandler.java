package dtu.example.handler;

import java.util.List;

import dtu.example.model.Activity;
import dtu.example.model.DbContext;

public class ActivityHandler {
    
    private DbContext dbContext;

    public ActivityHandler(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public void createActivity(int projectNumber, String name, int activityNumber) {
        Activity activity = new Activity(activityNumber, name, projectNumber);
        dbContext.activities.add(activity);
    }

    public List<Activity> getAllUserActivities(String userInitials) {
        return dbContext.activities.stream()
                .filter(activity -> activity.getUserInitials().contains(userInitials))
                .toList();
    }

    public void assignUserToActivity(int activityNumber, String userInitials) {
        Activity activity = dbContext.activities.stream()
                .filter(a -> a.getNumber() == activityNumber)
                .findFirst()
                .orElse(null);
        if (activity != null) {
            activity.addUserInitials(userInitials);
        }
    }

    public List<Activity> getActivitiesByProjectNumber(long projectNumber) {
        return dbContext.activities.stream()
                .filter(activity -> activity.getProjectNumber() == projectNumber)
                .toList();
    }

    public List<String> getUsersByActivityNumber(int activityNumber) {
        return dbContext.activities.stream()
                .filter(activity -> activity.getNumber() == activityNumber)
                .flatMap(activity -> activity.getUserInitials().stream())
                .toList();
    }

    public String getUserInitialsAsString(int activityNumber) {
        return getUsersByActivityNumber(activityNumber).stream()
                .reduce("", (a, b) -> a + ", " + b);
    }
}