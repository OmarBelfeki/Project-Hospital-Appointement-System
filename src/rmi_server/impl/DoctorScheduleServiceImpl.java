package rmi_server.impl;

import rmi_server.DoctorScheduleService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DoctorScheduleServiceImpl extends UnicastRemoteObject implements DoctorScheduleService {

    public DoctorScheduleServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<String> getAvailableSlots(String doctorId) throws RemoteException {
        return DataStore.slots.getOrDefault(doctorId, new java.util.concurrent.CopyOnWriteArrayList<>());
    }

    @Override
    public boolean addAvailableSlot(String doctorId, String slot) throws RemoteException {
        DataStore.slots.putIfAbsent(doctorId, new java.util.concurrent.CopyOnWriteArrayList<>());
        DataStore.slots.get(doctorId).add(slot);
        System.out.println("Added slot for " + doctorId + ": " + slot);
        return true;
    }

    @Override
    public boolean removeAvailableSlot(String doctorId, String slot) throws RemoteException {
        if (!DataStore.slots.containsKey(doctorId)) return false;
        boolean removed = DataStore.slots.get(doctorId).remove(slot);
        if (removed) System.out.println("Removed slot for " + doctorId + ": " + slot);
        return removed;
    }
}
