package ud2.examples.runnable;

import java.util.concurrent.ThreadLocalRandom;

class HelloRunnable implements Runnable {
    public void run(){
        for(int i = 1; i <= 5; i++) {
            int sleepTime = ThreadLocalRandom.current().nextInt(500, 1000);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("El hilo %s te saluda por %dª vez.\n",
                    Thread.currentThread().getName(), i
            );
        }
    }
}