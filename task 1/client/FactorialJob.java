package tcpWork.task2.client;

import tcpWork.task2.interfaces.Executable;

import java.io.Serializable;

public class FactorialJob implements Executable, Serializable {

    private final static long serialVersionUID = -1L;

    private int n;

    public FactorialJob() {
        this(0);
    }

    public FactorialJob(int n) {
        this.n = n;
    }

    // Деякі "важкі" розрахунки які будуть доручені серверу
    @Override
    public Object execute() {
        // в даному випадку розрахунок факторіалу числа
        long factorial = 1;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
