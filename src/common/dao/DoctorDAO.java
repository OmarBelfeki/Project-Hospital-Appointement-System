package common.dao;

import common.models.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    private static final Logger log = LoggerFactory.getLogger(DoctorDAO.class);
    private final Connection conn;

    public DoctorDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertDoctor(Doctor d) throws SQLException {
        System.out.println(d);
        String sql = "INSERT INTO doctors (doctor_id, name, specialization, password_hash) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, d.getId());
        ps.setString(2, d.getName());
        ps.setString(3, d.getSpecialization());
        ps.setString(4, d.getPasswordHash());
        return ps.executeUpdate() == 1;
    }

    public Doctor getDoctor(String id) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE doctor_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Doctor(
                    rs.getString("doctor_id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("password_hash")
            );
        }
        return null;
    }

    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> list = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT doctor_id, name, specialization, password_hash FROM doctors");
        while (rs.next()) {
            list.add(new Doctor(
                    rs.getString("doctor_id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("password_hash")
            ));
        }
        return list;
    }
}
