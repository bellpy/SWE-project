package dtu.example.handler;

import java.util.List;

import dtu.example.model.Activity;
import dtu.example.model.DbContext;

public class ActivityHandler {
    
    private DbContext dbContext;

    public ActivityHandler(DbContext dbContext) {
        this.dbContext = dbContext;
    }
    
    public int createActivity(long projectNumber, String name, int startWeek, int endWeek, int estimatedHours, List<String> userInitials) {
        int activityNumber = getNextActivityNumber(projectNumber);
        Activity activity = new Activity(activityNumber, name, projectNumber);
        activity.setStartWeek(startWeek);
        activity.setEndWeek(endWeek);
        activity.setEstimatedHours(estimatedHours);
        userInitials.forEach(activity::addUserInitials);
        dbContext.activities.add(activity);
        return activityNumber;
    }

    public void updateActivity(Activity activity) {
        // Assuming activity already has the updated data
        dbContext.activities.stream()
            .filter(a -> a.getNumber() == activity.getNumber())
            .findFirst()
            .ifPresent(existingActivity -> {
                existingActivity.setName(activity.getName());
                existingActivity.setStartWeek(activity.getStartWeek());
                existingActivity.setEndWeek(activity.getEndWeek());
                existingActivity.setEstimatedHours(activity.getEstimatedHours());
                existingActivity.getUserInitials().clear();
                existingActivity.getUserInitials().addAll(activity.getUserInitials());
            });
    }
    public int getNextActivityNumber(long projectNumber) {
        return dbContext.activities.stream()
            .mapToInt(Activity::getNumber)
            .max()
            .orElse(0) + 1;
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