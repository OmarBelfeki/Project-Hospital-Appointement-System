package rmi_server.impl;

import rmi_server.DoctorScheduleService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DoctorScheduleServiceImpl extends UnicastRemoteObject implements DoctorScheduleService {

    public DoctorScheduleServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<String> getAvailableSlots(String doctorId)
            throws RemoteException {
        return List.of("2025-01-01 10:00", "2025-01-02 14:00");
    }

    @Override
    public boolean addAvailableSlot(String doctorId, String slot)
            throws RemoteException {
        System.out.println("Slot added for " + doctorId + ": " + slot);
        return true;
    }

    @Override
    public boolean removeAvailableSlot(String doctorId, String slot)
            throws RemoteException {
        System.out.println("Slot removed for " + doctorId + ": " + slot);
        return true;
    }
}
