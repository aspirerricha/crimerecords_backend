import java.util.Date;

public class CrimeRecord {
    private String caseId;
    private String crimeType;
    private String location;
    private Date date;
    private String description;
    private String status;

    public CrimeRecord() {}

    public CrimeRecord(String caseId, String crimeType, String location, Date date, String description, String status) {
        this.caseId = caseId;
        this.crimeType = crimeType;
        this.location = location;
        this.date = date;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }

    public String getCrimeType() { return crimeType; }
    public void setCrimeType(String crimeType) { this.crimeType = crimeType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}