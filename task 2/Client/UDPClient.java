package udpWork.task1.Client;

import udpWork.task1.Server.ActiveUsers;
import udpWork.task1.Server.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class UDPClient {

    private final ActiveUsers userList;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private final int serverPort;
    private InetAddress serverAddress;

    public UDPClient(String server, int serverPort) {
        userList = new ActiveUsers();
        this.serverPort = serverPort;

        try {
            serverAddress = InetAddress.getByName(server);
            socket = new DatagramSocket();
            socket.setSoTimeout(3000);
        } catch (UnknownHostException | SocketException exception) {
            exception.printStackTrace();
        }
    }

    public void work(int bufferSize) throws ClassNotFoundException {
        byte[] buffer = new byte[bufferSize];
        try {
            packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Sending request");

            packet = new DatagramPacket(buffer, bufferSize);
            socket.receive(packet);
            int usersCount = (int) toObject(packet.getData());
            for (int i = 0; i < usersCount; i++) {
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                if (packet.getLength() == 0) {
                    break;
                }
                User usr = (User) toObject(packet.getData());
                userList.addUser(usr);
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        } finally {
            socket.close();
        }
        System.out.println("Registered users: " + userList.size());
        System.out.println(userList);
    }

    public Object toObject(byte[] arr) throws ClassNotFoundException, IOException {
        Object res;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(arr);
             ObjectInputStream ois = new ObjectInputStream(bais) ) {
            res = ois.readObject();
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            new UDPClient("localhost", 6666).work(256);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
