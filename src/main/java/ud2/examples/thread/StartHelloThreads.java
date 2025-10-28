package ud2.examples.thread;

import java.util.concurrent.ThreadLocalRandom;

public class StartHelloThreads {
    public static void main(String[] args) {
        HelloThread thread1 = new HelloThread("Hilo1");
        HelloThread thread2 = new HelloThread("Hilo2");
        HelloThread thread3 = new HelloThread("Hilo3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            for(int i = 1; i <= 5; i++) {
                int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
                    Thread.sleep(sleepTime);
                System.out.printf("El hilo principal te saluda por %dª vez.\n", i);
            }
        } catch (InterruptedException e) {
            System.out.println("El hilo principal ha sido interrumpido.");
        }
    }
}