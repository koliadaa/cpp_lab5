package udpWork.task1.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActiveUsers implements Serializable {

    List<User> users;

    public ActiveUsers(List<User> users) {
        this.users = users;
    }

    public ActiveUsers() {
        this(new ArrayList<>());
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean isEmpty() {
        return size() < 1;
    }

    public int size() {
        return users.size();
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public void clear() {
        users.clear();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (User u : users) {
            buf.append(u).append(System.lineSeparator());
        }
        return buf.toString();
    }
}
