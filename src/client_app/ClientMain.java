package client_app;

import client_app.rmi.RMIClient;
import client_app.chat.ChatClient;
import client_app.rest.RestClient;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws RemoteException {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Hospital System Client ===");

        // --- REST LOGIN ---
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        RestClient rest = new RestClient("http://localhost:8080/api");
        String loginResponse = rest.login(username, password);
        System.out.println("Login Response: " + loginResponse);

        // --- RMI CLIENT ---
        RMIClient rmi = new RMIClient("localhost");

        boolean running = true;
        while (running) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Book Appointment (RMI)");
            System.out.println("2. View Doctor Slots (RMI)");
            System.out.println("3. Chat (Socket)");
            System.out.println("4. Get Doctors (REST)");
            System.out.println("5. Exit");
            System.out.print("Select option: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Enter doctor ID: ");
                    String doctorId = sc.nextLine();
                    System.out.print("Enter date (yyyy-mm-dd hh:mm): ");
                    String date = sc.nextLine();
                    rmi.appointmentService.bookAppointment(username, doctorId, date);
                    System.out.println("Appointment booked (check server logs).");
                    break;

                case "2":
                    System.out.print("Enter doctor ID: ");
                    String dId = sc.nextLine();
                    System.out.println("Available slots: " + rmi.scheduleService.getAvailableSlots(dId));
                    break;

                case "3":
                    System.out.print("Enter chat server host (default localhost): ");
                    String host = sc.nextLine();
                    if (host.isEmpty()) host = "localhost";

                    ChatClient chat = new ChatClient(host, 5000, username);
                    System.out.println("Type messages, 'exit' to quit chat.");
                    while (true) {
                        String msg = sc.nextLine();
                        if (msg.equalsIgnoreCase("exit")) break;
                        chat.sendMessage(msg);
                    }
                    break;

                case "4":
                    System.out.println("Doctors: " + rest.getDoctors());
                    break;

                case "5":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        System.out.println("Client exited.");
        sc.close();
    }
}
