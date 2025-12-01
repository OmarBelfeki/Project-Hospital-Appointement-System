package chat_server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {

    // Thread-safe list of connected clients
    public static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        int port = 5000; // chat server port

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server running on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);

                // Create handler thread
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);

                handler.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
