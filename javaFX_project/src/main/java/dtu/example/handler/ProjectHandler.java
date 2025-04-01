package dtu.example.handler;

import dtu.example.model.Project;
import dtu.example.model.DbContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class ProjectHandler {

    private final DbContext dbContext;

    public ProjectHandler(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Project createProject(String name, String managerInitials) {
        int id = generateProjectId();
        Project project = new Project(name, id);
        project.addManager(managerInitials);
        dbContext.projects.add(project);
        return project;
    }

    public void addProject(Project project) {
        dbContext.projects.add(project);
    }

    public boolean removeProject(int id) {
        return dbContext.projects.removeIf(p -> p.id == id);
    }

    private int generateProjectId() {
        String todayPrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Find the highest suffix for today's projects using Java Streams (LINQ-like)
        int maxSuffix = dbContext.projects.stream()
                .map(p -> String.valueOf(p.id))
                .filter(id -> id.startsWith(todayPrefix))
                .mapToInt(id -> Integer.parseInt(id.substring(8))) // Get only the suffix
                .max()
                .orElse(0);

        int nextSuffix = maxSuffix + 1;

        // Return full ID: yyyymmdd + 4-digit suffix
        return Integer.parseInt(todayPrefix + String.format("%04d", nextSuffix));
    }
}
