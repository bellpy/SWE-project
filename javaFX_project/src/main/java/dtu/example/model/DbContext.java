package dtu.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

public class DbContext {
    public List<User> users;
    public List<Project> projects;
    public List<Activity> activities;
    public List<TimeRegistration> timeRegistrations;

    public DbContext() {
        users = new ArrayList<>();
        projects = new ArrayList<>();
        activities = new ArrayList<>();
        timeRegistrations = new ArrayList<>();
        initializeData();
    }

    public void initializeData() {
        // Users
        User user1 = new User("AB");
        User huba = new User("huba");
        this.users.addAll(List.of(user1, huba));

        //Projects
        Project project1 = new Project("Project1", 1);
        project1.addManagers(new ArrayList<>(List.of("AB")));
        Project project2 = new Project("Project2", 2);
        project2.addManagers(new ArrayList<>(List.of("CD")));
        this.projects.addAll(List.of(project1, project2));

        // Activities
        Activity activity1 = new Activity(1, "Development", project1.getId()); // projectNumber, name, activityNumber
        activity1.addUserInitials(user1.initials);
        activity1.addUserInitials(huba.initials);
        this.activities.add(activity1);

        // Time Registrations
        LocalDate date = LocalDate.now();
        TimeRegistration timeRegistration1 = new TimeRegistration(huba.initials, activity1.getNumber(), 8, date.minusDays(1));
        this.timeRegistrations.add(timeRegistration1);
    }
}
