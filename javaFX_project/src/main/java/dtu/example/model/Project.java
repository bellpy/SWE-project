package dtu.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private List<String> Managers;
    public long id;
    private String description;
    public LocalDate dateCreated;

    public Project(String name, long id) {
        this.name = name;
        this.id = id;
        this.Managers = new ArrayList<>();
        this.description = "";
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

    public void setDescription(String description) {
        this.description = description;
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
}
