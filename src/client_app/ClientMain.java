package client_app;

import client_app.rmi.RMIClient;
import client_app.chat.ChatClient;
import common.models.Appointment;
import common.models.Doctor;

import java.util.List;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Hospital System (RMI + Chat) ===");

        System.out.print("RMI host (default localhost): ");
        String rmiHost = sc.nextLine();
        if (rmiHost.isEmpty()) rmiHost = "localhost";

        RMIClient rmi = new RMIClient(rmiHost);

        boolean finished = false;

        while (!finished) {

            boolean loggedIn = false;
            String role = null;
            String userId = null;

            // LOGIN / REGISTER MENU
            while (!loggedIn) {
                System.out.println("\n=== Login Menu ===");
                System.out.println("1. Register Doctor");
                System.out.println("2. Login Doctor");
                System.out.println("3. Register Patient");
                System.out.println("4. Login Patient");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = sc.nextLine();

                switch (choice) {
                    case "1": // Register Doctor
                        System.out.print("Doctor ID: ");
                        String did = sc.nextLine();
                        System.out.print("Doctor Name: ");
                        String name = sc.nextLine();
                        System.out.print("Specialization: ");
                        String spec = sc.nextLine();
                        System.out.print("Password: ");
                        String dpass = sc.nextLine();

                        boolean added = rmi.doctorService.registerDoctor(did, name, spec, dpass);
                        System.out.println(added ? "Doctor registered successfully." : "Doctor ID already exists.");
                        break;

                    case "2": // Login Doctor
                        System.out.print("Doctor ID: ");
                        String docId = sc.nextLine();
                        System.out.print("Password: ");
                        String docPass = sc.nextLine();

                        boolean ok = rmi.doctorService.loginDoctor(docId, docPass);
                        if (ok) {
                            loggedIn = true;
                            userId = docId;
                            role = "doctor";
                            System.out.println("Doctor logged in successfully!");
                        } else {
                            System.out.println("Invalid ID/password.");
                        }
                        break;

                    case "3": // Register Patient
                        System.out.print("Patient ID: ");
                        String pid = sc.nextLine();
                        System.out.print("Patient Name: ");
                        String pname = sc.nextLine();
                        System.out.print("Password: ");
                        String ppass = sc.nextLine();

                        boolean okReg = rmi.patientService.registerPatient(pid, pname, ppass);
                        System.out.println(okReg ? "Patient registered successfully!" : "Patient ID already exists.");
                        break;

                    case "4": // Login Patient
                        System.out.print("Patient ID: ");
                        String lp = sc.nextLine();
                        System.out.print("Password: ");
                        String lpp = sc.nextLine();

                        boolean loggedP = rmi.patientService.loginPatient(lp, lpp);
                        if (loggedP) {
                            loggedIn = true;
                            userId = lp;
                            role = "patient";
                            System.out.println("Patient logged in successfully!");
                        } else {
                            System.out.println("Invalid ID/password.");
                        }
                        break;

                    case "5":
                        System.out.println("Goodbye!");
                        finished = true;
                        loggedIn = true; // exit menu
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            }

            if (finished) break;

            // MAIN SESSION MENU
            boolean session = true;
            while (session) {
                System.out.println("\n--- Menu (" + role + ": " + userId + ") ---");

                if (role.equals("patient")) {
                    System.out.println("1. List Doctors");
                    System.out.println("2. Book Appointment");
                    System.out.println("3. View My Appointments");
                    System.out.println("4. Cancel Appointment");
                    System.out.println("5. Chat with Doctor");
                    System.out.println("6. Logout");
                } else { // doctor
                    System.out.println("1. View My Appointments");
                    System.out.println("2. Add Available Slot");
                    System.out.println("3. Remove Available Slot");
                    System.out.println("4. Chat with Patient");
                    System.out.println("5. Logout");
                }

                System.out.print("Select option: ");
                String opt = sc.nextLine();

                try {
                    if (role.equals("patient")) {
                        switch (opt) {
                            case "1":
                                List<Doctor> docs = rmi.doctorService.getAllDoctors();
                                if (docs.isEmpty()) System.out.println("No doctors registered.");
                                else {
                                    System.out.println("Doctors:");
                                    for (Doctor d : docs) System.out.println(" - " + d);
                                }
                                break;

                            case "2":
                                System.out.print("Enter doctorId: ");
                                String did = sc.nextLine();
                                System.out.print("Enter date (YYYY-MM-DD HH:MM:SS): ");
                                String date = sc.nextLine();
                                String aptId = rmi.appointmentService.bookAppointment(userId, did, date);
                                System.out.println("Booked: " + aptId);
                                break;

                            case "3":
                                List<Appointment> appts = rmi.appointmentService.getPatientAppointments(userId);
                                if (appts.isEmpty()) System.out.println("No appointments.");
                                else {
                                    System.out.println("Appointments:");
                                    appts.forEach(a -> System.out.println(" - " + a));
                                }
                                break;

                            case "4":
                                System.out.print("Enter appointmentId to cancel: ");
                                String aid = sc.nextLine();
                                boolean c = rmi.appointmentService.cancelAppointment(aid);
                                System.out.println(c ? "Cancelled." : "Not found.");
                                break;

                            case "5":
                                System.out.print("Chat server host (default localhost): ");
                                String host = sc.nextLine();
                                if (host.isEmpty()) host = "localhost";

                                System.out.print("Enter doctorId to chat with: ");
                                String target = sc.nextLine();

                                ChatClient chat = new ChatClient(host, 5000, userId, "patient");
                                Thread t = new Thread(chat::receiveLoop);
                                t.start();

                                System.out.println("Type messages. 'exit' to quit chat.");
                                while (true) {
                                    String msg = sc.nextLine();
                                    if (msg.equalsIgnoreCase("exit")) {
                                        chat.close();
                                        break;
                                    }
                                    chat.sendMessage(target, msg);
                                }
                                break;

                            case "6":
                                session = false;
                                System.out.println("Logged out.");
                                break;

                            default:
                                System.out.println("Invalid option.");
                        }
                    } else { // doctor
                        switch (opt) {
                            case "1":
                                List<Appointment> doctorAppointments = rmi.appointmentService.getAppointmentsForDoctor(userId);
                                if (doctorAppointments.isEmpty()) System.out.println("No appointments.");
                                else {
                                    System.out.println("Your Appointments:");
                                    doctorAppointments.forEach(a -> System.out.println(" - " + a));
                                }
                                break;

                            case "2":
                                System.out.print("Enter slot (YYYY-MM-DD HH:MM:SS): ");
                                String slot = sc.nextLine();
                                rmi.scheduleService.addAvailableSlot(userId, slot);
                                System.out.println("Slot added.");
                                break;

                            case "3":
                                System.out.print("Enter slot to remove: ");
                                String slotR = sc.nextLine();
                                boolean rem = rmi.scheduleService.removeAvailableSlot(userId, slotR);
                                System.out.println(rem ? "Removed." : "Not found.");
                                break;

                            case "4":
                                System.out.print("Chat server host (default localhost): ");
                                String host2 = sc.nextLine();
                                if (host2.isEmpty()) host2 = "localhost";

                                System.out.print("Enter patient username to chat with: ");
                                String ptarget = sc.nextLine();

                                ChatClient docChat = new ChatClient(host2, 5000, userId, "doctor");
                                Thread t2 = new Thread(docChat::receiveLoop);
                                t2.start();

                                System.out.println("Type messages. 'exit' to quit chat.");
                                while (true) {
                                    String msg = sc.nextLine();
                                    if (msg.equalsIgnoreCase("exit")) {
                                        docChat.close();
                                        break;
                                    }
                                    docChat.sendMessage(ptarget, msg);
                                }
                                break;

                            case "5":
                                session = false;
                                System.out.println("Logged out.");
                                break;

                            default:
                                System.out.println("Invalid option.");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Goodbye. By Omar & Aziz");
        sc.close();
    }
}
