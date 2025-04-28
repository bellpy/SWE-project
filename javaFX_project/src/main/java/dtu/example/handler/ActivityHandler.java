package dtu.example.handler;

import dtu.example.model.Activity;
import dtu.example.model.DbContext;
import dtu.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class ActivityHandler {
    
    private final DbContext dbContext;

    public ActivityHandler(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public String calculateEndDate(Activity activity) {
        // logic to return calculated end date as String
        return "";
    }
    
    public void registerTime(Activity activity, User user) {
        // register time for an employee
    }
    
    public List<User> showEmployeeOverview(Activity activity) {
        // return a list of User objects
        return new ArrayList<>();
    }
    
    public void assignEmployee(Activity activity, String initials) {
        if (initials != null && !initials.isEmpty()) {
            // Find the user in the database
            User user = dbContext.users.stream()
                .filter(u -> u.initials.equals(initials))
                .findFirst()
                .orElse(null);
            
            if (user != null && user.calculateUserCapacity() > 0) {
                activity.getUserInitials().add(initials);
                user.assignActivity();
            }
        }
    }
    
    public Activity createActivity(int projectNumber, String name, int number) {
        return new Activity(number, name, projectNumber);
    }
}