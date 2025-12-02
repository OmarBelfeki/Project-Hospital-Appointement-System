package chat_server;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {
        int port = 5000;
        System.out.println("Starting chat server on port " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket s = serverSocket.accept();
                ClientHandler handler = new ClientHandler(s);
                handler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
