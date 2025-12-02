package common.models;

import java.io.Serializable;

public class Doctor implements Serializable {

    private String id;
    private String name;
    private String passwordHash; // store hashed password

    public Doctor(String id, String name, String passwordHash) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // ✅ Getter for hashed password
    public String getPasswordHash() {
        return passwordHash;
    }

    // Optional: setter if you want to change password
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return id + " (" + name + ")";
    }
}
