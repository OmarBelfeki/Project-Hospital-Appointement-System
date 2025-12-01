package chat_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;  // doctor or patient id

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // First message from client = username
            this.username = reader.readLine();
            System.out.println("User joined: " + username);

            // Broadcast welcome
            broadcast(username + " joined the chat.");

            // Message loop
            String msg;
            while ((msg = reader.readLine()) != null) {

                System.out.println(username + ": " + msg);
                broadcast(username + ": " + msg);   // send to all
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    // Send message to all connected users
    private void broadcast(String message) {
        for (ClientHandler handler : ChatServer.clients) {
            handler.writer.println(message);
        }
    }

    private void disconnect() {
        try {
            System.out.println(username + " disconnected.");
            ChatServer.clients.remove(this);
            socket.close();
        } catch (Exception ignored) {}
    }
}
