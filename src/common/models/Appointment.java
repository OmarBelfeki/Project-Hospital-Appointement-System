package common.models;

import java.io.Serializable;

public class Appointment implements Serializable {

    private String id;
    private String patientId;
    private String doctorId;
    private String date;

    public Appointment() {}

    public Appointment(String id, String patientId, String doctorId, String date) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getDate() { return date; }

    @Override
    public String toString() {
        return id + " | Patient: " + patientId + " | Doctor: " + doctorId + " | Date: " + date;
    }
}
