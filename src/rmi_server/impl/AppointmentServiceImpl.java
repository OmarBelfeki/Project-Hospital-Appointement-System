package rmi_server.impl;

import rmi_server.AppointmentService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceImpl extends UnicastRemoteObject
        implements AppointmentService {

    public AppointmentServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String bookAppointment(String patientId, String doctorId, String date)
            throws RemoteException {
        System.out.println("Booking appointment: " + patientId + " with " + doctorId);
        return "APT-" + System.currentTimeMillis(); // dummy ID
    }

    @Override
    public boolean cancelAppointment(String appointmentId)
            throws RemoteException {
        System.out.println("Cancel appointment: " + appointmentId);
        return true;
    }

    @Override
    public List<String> getPatientAppointments(String patientId)
            throws RemoteException {
        return new ArrayList<>();
    }
}
