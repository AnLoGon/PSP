package ud2.exercises.runcounterthreads;

public class CounterThread extends Thread {
    private String name;
    private int start;
    private int end;
    private int delay;

    public CounterThread(String name, int start, int end, int delay) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.delay = delay;
    }

    public void run() {
        for (int i = start; i <= end; i++) {
            System.out.printf("%s: %d\n", name, i);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.printf("%s ha sigut interromput\n", name);
            }
        }
    }
}
