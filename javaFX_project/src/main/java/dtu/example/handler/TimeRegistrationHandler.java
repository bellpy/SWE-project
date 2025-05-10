package dtu.example.handler;

import java.time.LocalDate;
import java.util.List;

import dtu.example.model.DbContext;
import dtu.example.handler.interfaces.ITimeRegistrationHandler;

public class TimeRegistrationHandler extends BaseHandler implements ITimeRegistrationHandler {
    
    public TimeRegistrationHandler(DbContext dbContext) {
        super(dbContext);
    }

    public void registerTime(String userInitials, int activityNumber, double hoursWorked, int daysAgo) {
        LocalDate date = LocalDate.now().minusDays(daysAgo);
        dbContext.timeRegistrations.add(new dtu.example.model.TimeRegistration(userInitials, activityNumber, hoursWorked, date));
    }

    public List<dtu.example.model.TimeRegistration> getTimeRegistrationsByUserAndActivityNumber(String userInitials, int activityNumber) {
        return dbContext.timeRegistrations.stream()
                .filter(tr -> tr.getUserInitials().equals(userInitials) && tr.getActivityNumber() == activityNumber)
                .toList();
    }

    public double getTotalRegisteredHoursByActivity(int activityNumber) {
        return dbContext.timeRegistrations.stream()
                .filter(tr -> tr.getActivityNumber() == activityNumber)
                .mapToDouble(tr -> tr.getHours())
                .sum();
    }
}
