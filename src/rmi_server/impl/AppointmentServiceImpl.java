package rmi_server.impl;

import rmi_server.AppointmentService;
import common.models.Appointment;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AppointmentServiceImpl extends UnicastRemoteObject implements AppointmentService {

    private final AtomicInteger counter = new AtomicInteger(1);

    public AppointmentServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized String bookAppointment(String patientId, String doctorId, String date) throws RemoteException {
        if (patientId == null || doctorId == null || date == null) throw new RemoteException("Invalid args");
        if (!DataStore.doctors.containsKey(doctorId)) throw new RemoteException("Doctor not found: " + doctorId);

        String id = "APT-" + counter.getAndIncrement();
        Appointment apt = new Appointment(id, patientId, doctorId, date);
        DataStore.appointments.put(id, apt);

        System.out.println("Booked appointment: " + apt);
        return id;
    }

    @Override
    public synchronized boolean cancelAppointment(String appointmentId) throws RemoteException {
        Appointment removed = DataStore.appointments.remove(appointmentId);
        if (removed != null) {
            System.out.println("Cancelled appointment: " + removed);
            return true;
        } else {
            System.out.println("Cancel failed, not found: " + appointmentId);
            return false;
        }
    }

    @Override
    public List<String> getPatientAppointments(String patientId) throws RemoteException {
        List<String> out = new ArrayList<>();
        for (Appointment a : DataStore.appointments.values()) {
            if (a.getPatientId().equals(patientId)) out.add(a.toString());
        }
        return out;
    }

    @Override
    public List<String> getAppointmentsForDoctor(String doctorId) throws RemoteException {
        List<String> out = new ArrayList<>();
        for (Appointment a : DataStore.appointments.values()) {
            if (a.getDoctorId().equals(doctorId)) out.add(a.toString());
        }
        return out;
    }
}
