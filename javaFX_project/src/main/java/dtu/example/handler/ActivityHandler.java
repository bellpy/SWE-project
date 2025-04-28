package dtu.example.handler;

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
}