package common.models;

import java.io.Serializable;

public class Patient implements Serializable {

    private String id;
    private String passwordHash; // stored hashed

    public Patient() {}

    public Patient(String id, String passwordHash) {
        this.id = id;
        this.passwordHash = passwordHash;
    }

    public String getId() { return id; }
    public String getPasswordHash() { return passwordHash; }

    @Override
    public String toString() {
        return id;
    }
}
