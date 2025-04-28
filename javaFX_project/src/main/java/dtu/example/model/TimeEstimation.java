package dtu.example.model;

public class TimeEstimation {
    private int hoursToWork;
    private int weeksToWork;
    private String startDate;

    public TimeEstimation(int hoursToWork, int weeksToWork, String startDate) {
        this.hoursToWork = hoursToWork;
        this.weeksToWork = weeksToWork;
        this.startDate = startDate;
    }
    
    public int getHoursToWork() {
        return hoursToWork;
    }

    public int getWeeksToWork() {
        return weeksToWork;
    }

    public String getStartDate() {
        return startDate;
    }
}
