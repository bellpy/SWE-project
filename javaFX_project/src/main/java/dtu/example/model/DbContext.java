package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class DbContext {
    public List<User> users;
    public List<Project> projects;
    public List<Activity> activities;

    public DbContext() {
        users = new ArrayList<>();
        projects = new ArrayList<>();
        activities = new ArrayList<>();

        initializeData();
    }

    public void initializeData() {
        User user1 = new User("AB");
        User huba = new User("huba");
        this.users.addAll(List.of(user1, huba));
        Project project1 = new Project("Project1", 1);
        
        project1.addManagers(new ArrayList<>(List.of("AB")));
        
        Project project2 = new Project("Project2", 2);
        project2.addManagers(new ArrayList<>(List.of("CD")));
        
        this.projects.addAll(List.of(project1, project2));

        Activity activity1 = new Activity(1, "Development", project1.getId()); // projectNumber, name, activityNumber
        activity1.addUserInitials(user1.initials);
        activity1.addUserInitials(huba.initials);
        this.activities.add(activity1);
    }
}
