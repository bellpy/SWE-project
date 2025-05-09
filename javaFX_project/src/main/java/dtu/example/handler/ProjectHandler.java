package dtu.example.handler;

import dtu.example.model.Project;
import dtu.example.model.DbContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjectHandler {

    private final DbContext dbContext;

    public ProjectHandler(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Project createProject(String name, List<String> managerInitials) {
        long id = generateProjectId();
        Project project = new Project(name, id);
    
        if (managerInitials != null) {
            project.addManagers(managerInitials);
        }
    
        project.dateCreated = LocalDate.now();
    
        dbContext.projects.add(project);
        return project;
    }
    

    public Project getProjectById(long id){
        return dbContext.projects.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Project> getAllProjects() {
        return dbContext.projects;
    }

    public void addProject(Project project) {
        dbContext.projects.add(project);
    }

    public boolean removeProject(int id) {
        return dbContext.projects.removeIf(p -> p.getId() == id);
    }

    private long generateProjectId() {
        String todayPrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    
        int maxSuffix = dbContext.projects.stream()
                .map(p -> String.valueOf(p.getId()))
                .filter(id -> id.startsWith(todayPrefix))
                .mapToInt(id -> Integer.parseInt(id.substring(8)))
                .max()
                .orElse(0);
    
        int nextSuffix = maxSuffix + 1;
    
        return Long.parseLong(todayPrefix + String.format("%04d", nextSuffix));
    }
}
