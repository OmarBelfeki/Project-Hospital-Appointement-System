package rmi_server;

import rmi_server.impl.AppointmentServiceImpl;
import rmi_server.impl.DoctorScheduleServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    public static void main(String[] args) {
        try {
            // Start RMI Registry on port 1099
            LocateRegistry.createRegistry(2002);
            System.out.println("RMI Registry started on port 2002.");

            // Create service instances
            AppointmentService appointmentService = new AppointmentServiceImpl();
            DoctorScheduleService doctorScheduleService = new DoctorScheduleServiceImpl();

            // Bind services
            Naming.rebind("rmi://localhost:2002/AppointmentService", appointmentService);
            Naming.rebind("rmi://localhost:2002/DoctorScheduleService", doctorScheduleService);

            System.out.println("RMI Services are running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
