package ud2.exercises.runcounterthreads;

public class RunCounterThreads {
    public static void main(String[] args) {
        CounterThread f1 = new CounterThread("FIL1", 1, 10, 1000);
        CounterThread f2 = new CounterThread("FIL2", 10, 100, 100);
        CounterThread f3 = new CounterThread("FIL3", 25, 50, 400);
        CounterThread f4 = new CounterThread("FIL4", 1, 5, 1300);

        f1.start();
        f2.start();
        f3.start();
        f4.start();
    }
}
