package dtu.example.handler;

import dtu.example.handler.interfaces.IHandler;
import dtu.example.model.DbContext;

public class BaseHandler implements IHandler{
    protected final DbContext dbContext;

    public BaseHandler(DbContext dbContext) {
        this.dbContext = dbContext;
        initialize();
    }

    @Override
    public void initialize() {
    }
}
