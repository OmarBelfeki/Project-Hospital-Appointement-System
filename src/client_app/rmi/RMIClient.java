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
            appointmentService = (AppointmentService) Naming.lookup("rmi://" + host + ":1099/AppointmentService");
            scheduleService = (DoctorScheduleService) Naming.lookup("rmi://" + host + ":1099/DoctorScheduleService");
            doctorService = (DoctorService) Naming.lookup("rmi://" + host + ":1099/DoctorService");
            patientService = (PatientService) Naming.lookup("rmi://" + host + ":1099/PatientService");
            System.out.println("Connected to RMI services at " + host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
