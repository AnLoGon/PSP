package ud2.examples.join;

import java.util.List;

public class StartSleepThreads {
    public static void main(String[] args) {
        List<SleepThread> threads = List.of(
                new SleepThread("Hilo 1", 2000),
                new SleepThread("Hilo 2", 1000),
                new SleepThread("Hilo 3", 500)
        );

        try {
            for(SleepThread thread : threads) {
                thread.start();
                System.out.printf("El hilo %s ha comenzado.\n", thread.getName());
                thread.join(); // Espera a que el hilo acabe
                System.out.printf("El hilo %s ha acabado.\n", thread.getName());
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrumpido.");
        }

        System.out.println("Todos los hilos han acabado.");
    }
}