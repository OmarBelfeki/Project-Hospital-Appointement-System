package rmi_server;

import common.models.Doctor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DoctorService extends Remote {
    boolean registerDoctor(String doctorId, String name, String specialization, String password) throws RemoteException;
    boolean loginDoctor(String doctorId, String password) throws RemoteException;
    List<Doctor> getAllDoctors() throws RemoteException;
}
