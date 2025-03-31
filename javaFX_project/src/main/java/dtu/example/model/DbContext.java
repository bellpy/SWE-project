package dtu.example.model;

import java.util.ArrayList;
import java.util.List;

public class DbContext {
    public List<User> users;

    public DbContext() {
        users = new ArrayList<>();
    }

    public void initializeData() {
        User user1 = new User("AB");
        this.users.add(user1);
    }
}
