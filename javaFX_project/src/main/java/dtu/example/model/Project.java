package dtu.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private List<String> Managers;
    public long id;
    public LocalDate dateCreated;

    public Project(String name, long id) {
        this.name = name;
        this.id = id;
        this.Managers = new ArrayList<>();
        this.dateCreated = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
    
    public void addManagers(List<String> managers) {
        Managers.addAll(managers);
    }
    
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public List<String> getManagers() {
        return Managers;
    }

    public String getManagersAsString() {
        return String.join(", ", Managers);
    }

    public String getProjectDetails() {
        return String.format(
            "Project Name: %s, ID: %d, Date Created: %s, Managers: %s",
            name,
            id,
            getDateCreated().toString(),
            getManagersAsString().isEmpty() ? "None" : getManagersAsString(),
            getManagers().isEmpty() ? "None" : getManagers()
        );
    }
}
