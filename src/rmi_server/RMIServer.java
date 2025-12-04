package rmi_server;

import rmi_server.impl.AppointmentServiceImpl;
import rmi_server.impl.DoctorScheduleServiceImpl;
import rmi_server.impl.DoctorServiceImpl;
import rmi_server.impl.PatientServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry started on 1099");

            AppointmentServiceImpl appointmentService = new AppointmentServiceImpl();
            DoctorScheduleServiceImpl scheduleService = new DoctorScheduleServiceImpl();
            DoctorServiceImpl doctorService = new DoctorServiceImpl();
            PatientServiceImpl patientService = new PatientServiceImpl();

            Naming.rebind("AppointmentService", appointmentService);
            Naming.rebind("DoctorScheduleService", scheduleService);
            Naming.rebind("DoctorService", doctorService);
            Naming.rebind("PatientService", patientService);

            System.out.println("RMI services bound.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
