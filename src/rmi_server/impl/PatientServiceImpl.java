package rmi_server.impl;

import rmi_server.PatientService;
import common.dao.PatientDAO;
import common.database.Database;
import common.models.Patient;
import common.utils.PasswordUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class PatientServiceImpl extends UnicastRemoteObject implements PatientService {

    private final PatientDAO dao;

    public PatientServiceImpl() throws RemoteException {
        super();
        try {
            Connection conn = Database.getConnection();
            dao = new PatientDAO(conn);
        } catch (Exception e) {
            throw new RemoteException("DB connection failed: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean registerPatient(String patientId, String name, String password) throws RemoteException {
        try {
            if (dao.getPatient(patientId) != null) return false;
            String hash = PasswordUtils.sha256Hex(password);
            Patient p = new Patient(patientId, name, hash);
            return dao.insertPatient(p);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public boolean loginPatient(String patientId, String password) throws RemoteException {
        try {
            Patient p = dao.getPatient(patientId);
            if (p == null) return false;
            return PasswordUtils.verify(password, p.getPasswordHash());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }
}
