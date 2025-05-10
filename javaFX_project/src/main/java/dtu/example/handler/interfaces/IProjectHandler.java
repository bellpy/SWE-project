package dtu.example.handler.interfaces;

import dtu.example.model.Project;
import java.util.List;

public interface IProjectHandler {
    Project createProject(String name, List<String> managerInitials);
    Project getProjectById(long id);
    String getProjectDetailsById(long id);
    List<Project> getAllProjects();
}
