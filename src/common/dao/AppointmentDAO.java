package common.dao;

import common.models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    private final Connection conn;

    public AppointmentDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertAppointment(String id, String patientId, String doctorId, String date) throws SQLException {
        String sql = "INSERT INTO appointments (appointment_id, patient_id, doctor_id, date) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, patientId);
        ps.setString(3, doctorId);
        ps.setString(4, date);
        return ps.executeUpdate() == 1;
    }

    public boolean deleteAppointment(String id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE appointment_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        return ps.executeUpdate() == 1;
    }

    public List<Appointment> getAppointmentsForPatient(String patientId) throws SQLException {
        String sql = "SELECT appointment_id, doctor_id, patient_id, date FROM appointments WHERE patient_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, patientId);
        ResultSet rs = ps.executeQuery();

        List<Appointment> list = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("appointment_id");
            String doctorId = rs.getString("doctor_id");
            String patientIdd = rs.getString("patient_id");
            String date = rs.getString("date");

            Appointment appointment = new Appointment(id, patientIdd, doctorId, date);
            list.add(appointment);
        }
        return list;
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorId) throws SQLException {
        String sql = "SELECT appointment_id, patient_id, doctor_id, date FROM appointments WHERE doctor_id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, doctorId);
        ResultSet rs = ps.executeQuery();

        List<Appointment> list = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("appointment_id");
            String patientId = rs.getString("patient_id");
            String doctorIdd = rs.getString("doctor_id");
            String date = rs.getString("date");

            Appointment appointment = new Appointment(id, patientId, doctorIdd, date);
            list.add(appointment);
        }
        return list;
    }
}
