package rmi_server.impl;

import rmi_server.AppointmentService;
import common.dao.AppointmentDAO;
import common.database.Database;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.List;

public class AppointmentServiceImpl extends UnicastRemoteObject implements AppointmentService {

    private final AppointmentDAO appointmentDAO;

    public AppointmentServiceImpl() throws RemoteException {
        super();
        try {
            Connection conn = Database.getConnection();
            appointmentDAO = new AppointmentDAO(conn);
        } catch (Exception e) {
            throw new RemoteException("DB connection failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String bookAppointment(String patientId, String doctorId, String date) throws RemoteException {
        String id = "APT-" + System.currentTimeMillis();
        try {
            boolean ok = appointmentDAO.insertAppointment(id, patientId, doctorId, date);
            return ok ? id : null;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public boolean cancelAppointment(String appointmentId) throws RemoteException {
        try {
            return appointmentDAO.deleteAppointment(appointmentId);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getPatientAppointments(String patientId) throws RemoteException {
        try {
            return appointmentDAO.getAppointmentsForPatient(patientId);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getAppointmentsForDoctor(String doctorId) throws RemoteException {
        try {
            return appointmentDAO.getAppointmentsForDoctor(doctorId);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }
}
