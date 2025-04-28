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
        this.users.add(user1);

        Project project1 = new Project("Project1", 1);
        project1.addManagers(new ArrayList<>(List.of("AB")));
        Project project2 = new Project("Project2", 2);
        project1.addManagers(new ArrayList<>(List.of("AB")));
        this.projects.addAll(List.of(project1, project2));

        Activity activity1 = new Activity(1, "Development", 101); // projectNumber, name, activityNumber
        this.activities.add(activity1);
    }
}
