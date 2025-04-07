package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class DbContext {
    public List<User> users;
    public List<Project> projects;

    public DbContext() {
        users = new ArrayList<>();
        projects = new ArrayList<>();
        initializeData();
    }

    public void initializeData() {
        User user1 = new User("AB");
        this.users.add(user1);

        Project project1 = new Project("Project1", 1);
        project1.addManagers(new ArrayList<>(List.of("AB")));
        this.projects.add(project1);
    }
}
