package ud2.examples.interrupt;

public class StartInterruptedThreads {
    public static void main(String[] args) {
        Thread.currentThread().setName("Hilo principal");

        InterruptedThread thread1 = new InterruptedThread("Hilo1");
        InterruptedThread thread2 = new InterruptedThread("Hilo2");
        InterruptedThread thread3 = new InterruptedThread("Hilo3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(2000);
            thread1.interrupt();
            Thread.sleep(1000);
            thread2.interrupt();
            Thread.sleep(500);
            thread3.interrupt();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" interrumpido.");
        }
    }
}