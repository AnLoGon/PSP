package ud2.exercises.interruptcounterthreads;

public class InterruptCounterThreads {
    public static void main(String[] args) {
        InfiniteCounterThread f1 = new InfiniteCounterThread ("FIL1", 1, 1000);
        InfiniteCounterThread f2 = new InfiniteCounterThread ("FIL2", 10, 100);
        InfiniteCounterThread f3 = new InfiniteCounterThread ("FIL3", 25, 400);
        InfiniteCounterThread f4 = new InfiniteCounterThread ("FIL4", 1, 1300);

        f1.start();
        f2.start();
        f3.start();
        f4.start();

        try {
            while (f1.getCont() < 10) {
                Thread.sleep(0);
            }
            f1.interrupt();

            while (f2.getCont() < 50) {
                Thread.sleep(0);
            }
            f2.interrupt();

            Thread.sleep(3000);
            f3.interrupt();

            int f4Cont = f4.getCont() + 10;
            while (f4.getCont() < f4Cont) {
                Thread.sleep(0);
            }
            f4.interrupt();
        } catch (InterruptedException e) {
            System.out.println("El fil principal ha sigut interromput");
        }
    }
}
