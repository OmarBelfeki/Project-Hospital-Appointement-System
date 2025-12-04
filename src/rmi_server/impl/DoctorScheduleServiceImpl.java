package rmi_server.impl;

import rmi_server.DoctorScheduleService;
import common.dao.DoctorSlotDAO;
import common.database.Database;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.List;

public class DoctorScheduleServiceImpl extends UnicastRemoteObject implements DoctorScheduleService {

    private final DoctorSlotDAO slotDAO;

    public DoctorScheduleServiceImpl() throws RemoteException {
        super();
        try {
            Connection conn = Database.getConnection();
            slotDAO = new DoctorSlotDAO(conn);
        } catch (Exception e) {
            throw new RemoteException("DB connection failed: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getAvailableSlots(String doctorId) throws RemoteException {
        try {
            return slotDAO.getSlots(doctorId);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public boolean addAvailableSlot(String doctorId, String slot) throws RemoteException {
        try {
            return slotDAO.addSlot(doctorId, slot);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public boolean removeAvailableSlot(String doctorId, String slot) throws RemoteException {
        try {
            return slotDAO.removeSlot(doctorId, slot);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }
}
