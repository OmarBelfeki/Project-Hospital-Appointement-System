package common.models;

import java.io.Serializable;

public class Doctor implements Serializable {

    private String id;
    private String name;
    private String specialization;
    private String passwordHash;

    public Doctor() {}

    public Doctor(String id, String name, String specialization, String passwordHash) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.passwordHash = passwordHash;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getPasswordHash() { return passwordHash; }

    @Override
    public String toString() {
        return id + " | " + name + " (" + specialization + ")";
    }
}
