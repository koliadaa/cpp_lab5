package tcpWork.task2.server;

import tcpWork.task2.interfaces.Result;

import java.io.Serializable;

public class ResultImpl implements Result, Serializable {
    Object output;
    double scoreTime;

    public ResultImpl(Object o, double c) {
        output = o;
        scoreTime = c;
    }

    @Override
    public Object output() {
        return output;
    }

    @Override
    public double scoreTime() {
        return scoreTime;
    }
}
