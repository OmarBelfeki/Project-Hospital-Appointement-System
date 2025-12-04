package rmi_server.impl;

import rmi_server.DoctorService;
import common.models.Doctor;
import common.dao.DoctorDAO;
import common.database.Database;
import common.utils.PasswordUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.List;

public class DoctorServiceImpl extends UnicastRemoteObject implements DoctorService {

    private final DoctorDAO dao;

    public DoctorServiceImpl() throws RemoteException {
        super();
        try {
            Connection conn = Database.getConnection();
            dao = new DoctorDAO(conn);
        } catch (Exception e) {
            throw new RemoteException("DB connection failed: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean registerDoctor(String doctorId, String name, String specialization, String password) throws RemoteException {
        try {
            if (dao.getDoctor(doctorId) != null) return false;
            String hash = PasswordUtils.sha256Hex(password);
            Doctor d = new Doctor(doctorId, name, specialization, hash);
            return dao.insertDoctor(d);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public boolean loginDoctor(String doctorId, String password) throws RemoteException {
        try {
            Doctor d = dao.getDoctor(doctorId);
            if (d == null) return false;
            return PasswordUtils.verify(password, d.getPasswordHash());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public List<Doctor> getAllDoctors() throws RemoteException {
        try {
            return dao.getAllDoctors();
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }
}
