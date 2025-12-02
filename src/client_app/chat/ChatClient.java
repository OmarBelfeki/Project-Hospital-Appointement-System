package client_app.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private String role;

    public ChatClient(String host, int port, String username, String role) throws Exception {
        this.username = username;
        this.role = role;
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // send first line: username|role
        out.println(username + "|" + role);
    }

    // send message to target user
    public void sendMessage(String target, String message) {
        out.println(target + "|" + message);
    }

    // blocking receive; usually run in separate thread
    public void receiveLoop() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                // server sends: "FROM|sender|message" or "WELCOME|username" or "ERROR|..."
                System.out.println("[CHAT] " + line);
            }
        } catch (Exception e) {
            System.out.println("Chat connection closed.");
        }
    }

    public void close() {
        try { socket.close(); } catch (Exception ignored) {}
    }
}
