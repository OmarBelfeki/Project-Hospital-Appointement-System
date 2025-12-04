package client_app.rmi;

import rmi_server.AppointmentService;
import rmi_server.DoctorScheduleService;
import rmi_server.DoctorService;
import rmi_server.PatientService;

import java.rmi.Naming;

public class RMIClient {
    public AppointmentService appointmentService;
    public DoctorScheduleService scheduleService;
    public DoctorService doctorService;
    public PatientService patientService;

    public RMIClient(String host) {
        try {
            String base = "rmi://" + host + ":1099/";
            appointmentService = (AppointmentService) Naming.lookup(base + "AppointmentService");
            scheduleService = (DoctorScheduleService) Naming.lookup(base + "DoctorScheduleService");
            doctorService = (DoctorService) Naming.lookup(base + "DoctorService");
            patientService = (PatientService) Naming.lookup(base + "PatientService");
            System.out.println("Connected to RMI services at " + host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
