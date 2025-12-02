package rmi_server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DoctorScheduleService extends Remote {
    List<String> getAvailableSlots(String doctorId) throws RemoteException;
    boolean addAvailableSlot(String doctorId, String slot) throws RemoteException;
    boolean removeAvailableSlot(String doctorId, String slot) throws RemoteException;
}
