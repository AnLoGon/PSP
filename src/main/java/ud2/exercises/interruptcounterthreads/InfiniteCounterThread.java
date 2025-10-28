package ud2.exercises.interruptcounterthreads;

public class InfiniteCounterThread extends Thread {
    private final String name;
    private int cont;
    private final int delay;

    public InfiniteCounterThread(String name, int start, int delay) {
        this.name = name;
        this.cont = start;
        this.delay = delay;
    }

    public int getCont() {
        return cont;
    }

    public void run() {
        try {
            while (!isInterrupted()) {
                System.out.printf("%s: %d\n", name, cont++);
                Thread.sleep(delay);
            }
        }
        catch (InterruptedException e) {
            System.out.printf("%s interromput: %d\n", name, cont);
        }
    }
}
