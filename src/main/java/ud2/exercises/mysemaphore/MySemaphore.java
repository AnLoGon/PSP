package ud2.exercises.mysemaphore;

public class MySemaphore {
    private int permits;
    private int activeThreads = 0;

    // Constructor
    public MySemaphore(int permits) {
        if (permits < 0) {
            throw new IllegalArgumentException("El número de permisos no puede ser negativo.");
        }
        this.permits = permits;
    }

    // Método acquire
    public synchronized void acquire() {
        while (permits == 0) {
            try {
                wait(); // Espera hasta que haya permisos disponibles
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restablecer estado de interrupción
                throw new RuntimeException("Hilo interrumpido", e);
            }
        }
        permits--; // Reduce el número de permisos disponibles
        activeThreads++; // Incrementa el número de hilos activos
        System.out.printf("(%d/%d) Hilo %s ha adquirido el recurso.%n", activeThreads, permits, Thread.currentThread().getName());
    }

    // Método release
    public synchronized void release() {
        permits++; // Incrementa el número de permisos disponibles
        activeThreads--; // Decrementa el número de hilos activos
        System.out.printf("(%d/%d) Hilo %s ha liberado el recurso.%n", activeThreads, permits, Thread.currentThread().getName());
        notify(); // Notifica a un hilo en espera
    }

    // Método availablePermits
    public synchronized int availablePermits() {
        return permits;
    }

    // Método getQueueLength
    public synchronized int getQueueLength() {
        // En una implementación básica con synchronized, no hay una forma directa de obtener la longitud de la cola.
        // Esto puede depender del sistema, pero aquí devolvemos una estimación no precisa.
        return Math.max(0, activeThreads + permits); // No perfecto, pero ilustrativo.
    }

    // Método setPermits
    public synchronized void setPermits(int permits) {
        if (permits < 0) {
            throw new IllegalArgumentException("El número de permisos no puede ser negativo.");
        }
        int additionalPermits = permits - this.permits;
        this.permits = permits;

        // Si se aumentan los permisos, notifica a los hilos esperando
        if (additionalPermits > 0) {
            notifyAll();
        }
    }

    public static void main(String[] args) {
        MySemaphore semaphore = new MySemaphore(2);
        Thread t1 = new Thread(() -> {
            semaphore.acquire();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Hilo interrumpido", e);
            }
            semaphore.release();
        });
        Thread t2 = new Thread(() -> {
            semaphore.acquire();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Hilo interrumpido", e);
            }
            semaphore.release();
        });
        Thread t3 = new Thread(() -> {
            semaphore.acquire();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Hilo interrumpido", e);
            }
            semaphore.release();
        });
        t1.start();
        t2.start();
        t3.start();
    }
}