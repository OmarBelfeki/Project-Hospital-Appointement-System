package rmi_server.impl;

import common.models.Doctor;
import common.models.Patient;
import common.models.Appointment;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class DataStore {
    // doctorId -> Doctor
    public static final Map<String, Doctor> doctors = new ConcurrentHashMap<>();

    // patientId -> Patient
    public static final Map<String, Patient> patients = new ConcurrentHashMap<>();

    // appointmentId -> Appointment
    public static final Map<String, Appointment> appointments = new ConcurrentHashMap<>();

    // doctor's available slots (doctorId -> list of slots)
    public static final Map<String, java.util.concurrent.CopyOnWriteArrayList<String>> slots =
            new ConcurrentHashMap<>();
}
