package rmi_server.impl;

import rmi_server.DoctorService;
import common.models.Doctor;
import common.utils.PasswordUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DoctorServiceImpl extends UnicastRemoteObject implements DoctorService {

    public DoctorServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized boolean registerDoctor(String doctorId, String name, String password) throws RemoteException {
        if (doctorId == null || name == null || password == null) return false;
        if (DataStore.doctors.containsKey(doctorId)) return false;
        String hash = PasswordUtils.sha256Hex(password);
        Doctor d = new Doctor(doctorId, name, hash);
        DataStore.doctors.put(doctorId, d);
        DataStore.slots.putIfAbsent(doctorId, new java.util.concurrent.CopyOnWriteArrayList<>());
        System.out.println("Registered doctor: " + d);
        return true;
    }

    @Override
    public boolean loginDoctor(String doctorId, String password) throws RemoteException {
        if (!DataStore.doctors.containsKey(doctorId)) return false;
        String hash = DataStore.doctors.get(doctorId).getPasswordHash();
        return PasswordUtils.verify(password, hash);
    }

    @Override
    public List<Doctor> getAllDoctors() throws RemoteException {
        return new ArrayList<>(DataStore.doctors.values());
    }

    @Override
    public boolean doctorExists(String doctorId) throws RemoteException {
        return DataStore.doctors.containsKey(doctorId);
    }
}
