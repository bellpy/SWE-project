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
        Activity activity2 = new Activity(2, "Design", project1.getId()); // projectNumber, name, activityNumber
        activity1.addUserInitials(user1.initials);
        activity1.addUserInitials(huba.initials);
        activity2.addUserInitials(huba.initials);
        this.activities.addAll(List.of(activity1, activity2));

        // Time Registrations
        LocalDate date = LocalDate.now();
        TimeRegistration timeRegistration1 = new TimeRegistration(huba.initials, activity1.getNumber(), 8, date.minusDays(1));
        TimeRegistration timeRegistration2 = new TimeRegistration(huba.initials, activity2.getNumber(), 4, date.minusDays(2));
        this.timeRegistrations.addAll(List.of(timeRegistration1, timeRegistration2));
        initializeAbsences();
    }

    private void initializeAbsences(){
        User user = new User("Boss");

        Project abscenceProject = new Project("Abscences", 0);
        abscenceProject.addManagers(new ArrayList<>(List.of(user.initials)));

        List<String> userInitials = this.users.stream()
                .map(User::getInitials)
                .toList();

        this.projects.add(abscenceProject);
        Activity abscence1 = new Activity( "Vacation", abscenceProject.getId());
        abscence1.setAllUserInitials(userInitials);
        Activity abscence2 = new Activity( "Sickness", abscenceProject.getId());
        abscence2.setAllUserInitials(userInitials);
        Activity abscence3 = new Activity( "Course", abscenceProject.getId());
        abscence3.setAllUserInitials(userInitials);
        Activity abscence4 = new Activity( "Emergency", abscenceProject.getId()); 
        abscence4.setAllUserInitials(userInitials);
        Activity abscence5 = new Activity( "Personal", abscenceProject.getId());
        abscence5.setAllUserInitials(userInitials);
        
        this.activities.addAll(List.of(abscence1, abscence2, abscence3, abscence4, abscence5));
    }
}
