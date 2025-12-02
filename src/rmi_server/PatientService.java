package rmi_server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PatientService extends Remote {
    boolean registerPatient(String patientId, String password) throws RemoteException;
    boolean loginPatient(String patientId, String password) throws RemoteException;
    boolean patientExists(String patientId) throws RemoteException;
}
