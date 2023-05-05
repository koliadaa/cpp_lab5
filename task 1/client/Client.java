package tcpWork.task2.client;

import tcpWork.task2.interfaces.Result;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    public Client(String server, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 1000);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 6666);

        ObjectOutputStream out = client.getOutputStream();
        String classfile = "out/production/JavaNetProgramming (part 2)/tcpWork/task2/client/FactorialJob.class";

        try {
            out.writeObject(classfile);
            FileInputStream fis = new FileInputStream(classfile);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            out.writeObject(bytes);

            FactorialJob aJob = new FactorialJob(3);
            out.writeObject(aJob);

            ObjectInputStream in = client.getInputStream();
            classfile = (String) in.readObject();
            bytes = (byte[]) in.readObject();

            FileOutputStream fos = new FileOutputStream(classfile);
            fos.write(bytes);

            Result result = (Result) in.readObject();
            System.out.println("result = " + result.output() + ", time taken = " + result.scoreTime() / 1000 + "ms");
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
