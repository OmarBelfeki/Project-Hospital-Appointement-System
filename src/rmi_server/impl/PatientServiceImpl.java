package rmi_server.impl;

import rmi_server.PatientService;
import common.models.Patient;
import common.utils.PasswordUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PatientServiceImpl extends UnicastRemoteObject implements PatientService {

    public PatientServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized boolean registerPatient(String patientId, String password) throws RemoteException {
        if (patientId == null || password == null) return false;
        if (DataStore.patients.containsKey(patientId)) return false;
        String hash = PasswordUtils.sha256Hex(password);
        Patient p = new Patient(patientId, hash);
        DataStore.patients.put(patientId, p);
        System.out.println("Registered patient: " + patientId);
        return true;
    }

    @Override
    public boolean loginPatient(String patientId, String password) throws RemoteException {
        if (!DataStore.patients.containsKey(patientId)) return false;
        String hash = DataStore.patients.get(patientId).getPasswordHash();
        return PasswordUtils.verify(password, hash);
    }

    @Override
    public boolean patientExists(String patientId) throws RemoteException {
        return DataStore.patients.containsKey(patientId);
    }
}
