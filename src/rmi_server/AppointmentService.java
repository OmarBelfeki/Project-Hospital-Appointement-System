package rmi_server;

import common.models.Appointment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AppointmentService extends Remote {
    String bookAppointment(String patientId, String doctorId, String date) throws RemoteException;
    boolean cancelAppointment(String appointmentId) throws RemoteException;
    List<Appointment> getPatientAppointments(String patientId) throws RemoteException;
    List<Appointment> getAppointmentsForDoctor(String doctorId) throws RemoteException;
}
