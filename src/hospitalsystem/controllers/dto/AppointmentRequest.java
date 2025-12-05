package hospitalsystem.controllers.dto;

public class AppointmentRequest {
    public String id;
    public String patientId;
    public String doctorId;
    public String date; // keep as String if DAO expects a String

    // Optional: constructor, getters, setters
    public AppointmentRequest() {}

    public AppointmentRequest(String id, String patientId, String doctorId, String date) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }
}
