package tcpWork.task2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private final int serverPort;

    public Server(int port) {
        serverPort = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server started!");
            while (true) {
                System.out.println("Waiting for client connection...");
                Socket socket = serverSocket.accept();
                System.out.println("New client with " + socket + " socket");
                ClientHandler ch = new ClientHandler(socket);
                ch.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                System.out.println("Server stopped");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server srv = new Server(6666);
        srv.start();
    }
}
