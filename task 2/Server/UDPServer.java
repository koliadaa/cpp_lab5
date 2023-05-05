package udpWork.task1.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {

    private final ActiveUsers userList;
    private DatagramSocket socket;
    private DatagramPacket packet;

    private InetAddress address;
    private int port;

    public UDPServer(int port) {
        userList = new ActiveUsers();

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException exception) {
            exception.printStackTrace();
        }
    }

    public void work(int bufferSize) {
        try {
            System.out.println("Server start...");
            while (true) {
                addActiveUser(bufferSize);
                log(address, port);
                sendUserData();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            System.out.println("Server end...");
            socket.close();
        }
    }

    private void addActiveUser(int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        address = packet.getAddress();
        port = packet.getPort();
        User usr = new User(address, port);

        if (!userList.contains(usr)) {
            userList.addUser(usr);
        }
    }

    private void log(InetAddress address, int port) {
        System.out.println(
                "Request from: " + address.getHostAddress() +
                        ", port: " + port
        );
    }

    private void sendUserData() throws IOException {
        byte[] buffer = toArray(userList.size());
        packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);

        for (int i = 0; i < userList.size(); i++) {
            buffer = toArray(userList.getUser(i));
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        }
    }

    public byte[] toArray(Object obj) throws IOException {
        byte[] arr;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            oos.flush();
            arr = baos.toByteArray();
        }
        return arr;
    }

    public static void main(String[] args) {
        new UDPServer(6666).work(256);
    }
}
