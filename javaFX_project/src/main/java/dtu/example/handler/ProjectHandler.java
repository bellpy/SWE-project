package dtu.example.handler;

import dtu.example.model.Project;
import dtu.example.model.DbContext;
import dtu.example.handler.interfaces.IProjectHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjectHandler extends BaseHandler implements IProjectHandler {

    public ProjectHandler(DbContext dbContext) {
        super(dbContext);
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

    public String getProjectDetailsById(long id) {
        Project project = getProjectById(id);
        if (project != null) {
            return project.getProjectDetails();
        } else {
            return "Project not found.";
        }
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

    public boolean manageProjects(Project projectToAdd, int projectIdToRemove) {

        addProject(projectToAdd);
    
        List<Project> allProjects = getAllProjects();
    
        boolean isRemoved = removeProject(projectIdToRemove);

        return true;
    }
}
