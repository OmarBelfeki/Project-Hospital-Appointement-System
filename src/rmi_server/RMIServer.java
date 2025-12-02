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

            AppointmentService appointmentService = new AppointmentServiceImpl();
            DoctorScheduleService scheduleService = new DoctorScheduleServiceImpl();
            DoctorService doctorService = new DoctorServiceImpl();
            PatientService patientService = new PatientServiceImpl();

            Naming.rebind("rmi://localhost:1099/AppointmentService", appointmentService);
            Naming.rebind("rmi://localhost:1099/DoctorScheduleService", scheduleService);
            Naming.rebind("rmi://localhost:1099/DoctorService", doctorService);
            Naming.rebind("rmi://localhost:1099/PatientService", patientService);

            System.out.println("RMI services bound: AppointmentService, DoctorScheduleService, DoctorService, PatientService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
