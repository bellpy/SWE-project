package dtu.example.handler.interfaces;

public interface IReportHandler {
    String generateAndSaveReport(long projectNumber);
    String getProjectReport(long projectNumber);
    void saveReportToFile(String report, long projectNumber);
}
