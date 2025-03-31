package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    public List<String> Managers;
    public String name;
    public int id;

    public Project(String name, int id) {
        this.name = name;
        this.id = id;
        Managers = new ArrayList<>();
    }
    
    public void addManager(String manager) {
        Managers.add(manager);
    }
}
