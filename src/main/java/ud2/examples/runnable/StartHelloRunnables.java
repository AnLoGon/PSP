package ud2.examples.runnable;

import java.util.concurrent.ThreadLocalRandom;

public class StartHelloRunnables {
    public static void main(String[] args) throws InterruptedException {
        HelloRunnable rc = new HelloRunnable();

        Thread thread1 = new Thread(rc);
        thread1.setName("Hilo1");
        Thread thread2 = new Thread(rc);
        thread2.setName("Hilo2");
        Thread thread3 = new Thread(rc);
        thread3.setName("Hilo3");

        thread1.start();
        thread2.start();
        thread3.start();

        for(int i = 1; i <= 5; i++) {
            int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
            Thread.sleep(sleepTime);
            System.out.printf("El hilo principal te saluda por %dª vez.\n", i);
        }
    }
}