package hospitalsystem.controllers.dto;

public class PatientRequest {
    public String id;
    public String name;
    public String passwordHash;

    public PatientRequest() { }

    public PatientRequest(String id, String name,  String passwordHash) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    // optionally: getters / setters
}
