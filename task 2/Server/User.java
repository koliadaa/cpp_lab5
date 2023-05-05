package udpWork.task1.Server;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    InetAddress address;
    int port;

    public User(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "User{" +
                "address=" + address +
                ", port=" + port +
                '}';
    }
}
