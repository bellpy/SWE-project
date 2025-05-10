package dtu.example.handler.interfaces;

import dtu.example.model.TimeRegistration;
import java.util.List;

public interface ITimeRegistrationHandler {
    void registerTime(String userInitials, int activityNumber, double hoursWorked, int daysAgo);
    List<TimeRegistration> getTimeRegistrationsByUserAndActivityNumber(String userInitials, int activityNumber);
    double getTotalRegisteredHoursByActivity(int activityNumber);
}
