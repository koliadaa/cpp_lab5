package tcpWork.task2.server;

import tcpWork.task2.interfaces.Executable;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Client Handler Started for " + socket);
            processOperation();
            System.out.println("Client Handler Stopped for: " + socket);
            socket.close();
        } catch (IOException | ClassNotFoundException error) {
            error.printStackTrace();
        }
    }

    private void processOperation() throws IOException, ClassNotFoundException {

        String classFile = (String) inputStream.readObject();
        classFile = classFile.replaceFirst("client", "server");
        byte[] b = (byte[]) inputStream.readObject();
        FileOutputStream fos = new FileOutputStream(classFile);
        fos.write(b);

        Executable ex = (Executable) inputStream.readObject();

        double startTime = System.nanoTime();
        Object output = ex.execute();
        double endTime = System.nanoTime();
        double completionTime = endTime - startTime;

        ResultImpl resultOutput = new ResultImpl(output, completionTime);
        classFile = "out/production/JavaNetProgramming (part 2)/tcpWork/task2/client/FactorialJob.class";
        outputStream.writeObject(classFile);
        FileInputStream fis = new FileInputStream(classFile);
        byte[] bo = new byte[fis.available()];
        fis.read(bo);
        outputStream.writeObject(bo);
        outputStream.writeObject(resultOutput);
    }
}
