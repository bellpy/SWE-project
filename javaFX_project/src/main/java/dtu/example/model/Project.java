package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    public List<String> Managers;
    public String name;
    public long id;

    public Project(String name, long id) {
        this.name = name;
        this.id = id;
        Managers = new ArrayList<>();
    }
    
    public void addManagers(List<String> managers) {
        Managers.addAll(managers);
    }
}
