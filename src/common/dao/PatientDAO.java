package common.dao;

import common.models.Patient;

import java.sql.*;

public class PatientDAO {

    private final Connection conn;

    public PatientDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertPatient(Patient p) throws SQLException {
        String sql = "INSERT INTO patients (patient_id, name, password_hash) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, p.getId());
        ps.setString(2, p.getName());
        ps.setString(3, p.getPasswordHash());
        return ps.executeUpdate() == 1;
    }

    public Patient getPatient(String id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE patient_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Patient(
                    rs.getString("patient_id"),
                    rs.getString("name"),
                    rs.getString("password_hash")
            );
        }
        return null;
    }
}
