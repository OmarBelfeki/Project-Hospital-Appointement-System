package client_app.rmi;

import rmi_server.AppointmentService;
import rmi_server.DoctorScheduleService;

import java.rmi.Naming;

public class RMIClient {

    public AppointmentService appointmentService;
    public DoctorScheduleService scheduleService;

    public RMIClient(String host) {
        try {
            appointmentService = (AppointmentService)
                    Naming.lookup("rmi://" + host + ":1099/AppointmentService");

            scheduleService = (DoctorScheduleService)
                    Naming.lookup("rmi://" + host + ":1099/DoctorScheduleService");

            System.out.println("Connected to RMI services.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bookAppointment() {
        try {
            String id = appointmentService.bookAppointment("P001", "D001", "2025-01-10");
            System.out.println("Appointment ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDoctorSlots() {
        try {
            System.out.println(scheduleService.getAvailableSlots("D001"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
