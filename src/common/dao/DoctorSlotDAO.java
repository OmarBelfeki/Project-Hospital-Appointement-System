package common.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorSlotDAO {

    private final Connection conn;

    public DoctorSlotDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addSlot(String doctorId, String slot) throws SQLException {
        String sql = "INSERT INTO doctor_slots (doctor_id, slot) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, doctorId);
        ps.setString(2, slot);
        return ps.executeUpdate() == 1;
    }

    public boolean removeSlot(String doctorId, String slot) throws SQLException {
        String sql = "DELETE FROM doctor_slots WHERE doctor_id=? AND slot=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, doctorId);
        ps.setString(2, slot);
        return ps.executeUpdate() == 1;
    }

    public List<String> getSlots(String doctorId) throws SQLException {
        String sql = "SELECT slot FROM doctor_slots WHERE doctor_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, doctorId);
        ResultSet rs = ps.executeQuery();

        List<String> list = new ArrayList<>();
        while (rs.next()) list.add(rs.getString("slot"));
        return list;
    }
}
