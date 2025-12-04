package common.models;

import java.io.Serializable;

public class Patient implements Serializable {

    private String id;
    private String name;
    private String passwordHash;

    public Patient() {}

    public Patient(String id, String name, String passwordHash) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPasswordHash() { return passwordHash; }

    @Override
    public String toString() {
        return id + " | " + name;
    }
}
