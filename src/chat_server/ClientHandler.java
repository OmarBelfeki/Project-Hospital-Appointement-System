package chat_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

// Protocol:
// First message: "username|role" (e.g. "omar|patient" or "doc01|doctor")
// Then messages: "target|message"
public class ClientHandler extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private String role;

    // Shared map of connected users (username -> handler)
    private static final Map<String, ClientHandler> connected = new ConcurrentHashMap<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void send(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String first = in.readLine();
            if (first == null) { socket.close(); return; }
            String[] parts = first.split("\\|", 2);
            username = parts[0];
            role = parts.length > 1 ? parts[1] : "patient";

            connected.put(username, this);
            System.out.println("Connected: " + username + " (" + role + ")");
            out.println("WELCOME|" + username);

            String line;
            while ((line = in.readLine()) != null) {
                int idx = line.indexOf('|');
                if (idx <= 0) continue;
                String target = line.substring(0, idx);
                String message = line.substring(idx + 1);

                ClientHandler targetHandler = connected.get(target);
                if (targetHandler != null) {
                    targetHandler.send("FROM|" + username + "|" + message);
                } else {
                    out.println("ERROR|User not online: " + target);
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            try {
                if (username != null) {
                    connected.remove(username);
                    System.out.println("Disconnected: " + username);
                }
                socket.close();
            } catch (Exception ignored) {}
        }
    }
}
