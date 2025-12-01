package client_app.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ChatClient(String host, int port, String username) {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Send username first
            writer.println(username);

            // Start listening for incoming messages
            new Thread(() -> receiveMessages()).start();

            System.out.println("Connected to chat server.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveMessages() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                System.out.println("MESSAGE: " + msg);
            }
        } catch (Exception e) {
            System.out.println("Chat connection closed.");
        }
    }

    public void sendMessage(String msg) {
        writer.println(msg);
    }
}
