package dtu.example.handler.interfaces;

import dtu.example.model.Activity;
import java.util.List;

public interface IActivityHandler {
    int createActivity(long projectNumber, String name, int startWeek, int endWeek, int estimatedHours, List<String> userInitials);
    void updateActivity(Activity activity);
    List<Activity> getAllUserActivities(String userInitials);
    List<Activity> getActivitiesByProjectNumber(long projectNumber);
}
