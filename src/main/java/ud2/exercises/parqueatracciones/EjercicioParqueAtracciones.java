package ud2.exercises.parqueatracciones;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class EjercicioParqueAtracciones {
    public static void main(String[] args) throws InterruptedException {
        ParqueAtracciones parque = new ParqueAtracciones(3); // Capacidad máxima de 3 personas
        System.out.println("Parque de Atracciones abierto con capacidad para " +
                parque.getCapacidadMaxima() + " personas.");
        int numVisitantes = 7;
        Thread[] visitantes = new Thread[numVisitantes];

        for (int i = 0; i < numVisitantes; i++) {
            visitantes[i] = new Thread(new Visitante(parque, "Visitante " +
                    (i + 1)), "Visitante " + (i + 1));
            visitantes[i].start();
        }

        // Dejar que la simulación corra por un tiempo y luego interrumpir a los visitantes si es necesario
        // (Para este ejercicio simple, solo esperamos que terminen naturalmente)
        for (Thread visitante : visitantes)
            visitante.join();

        System.out.println("Todos los visitantes han finalizado. Parque cerrando.");
    }
}

class ParqueAtracciones {
    private Semaphore semaforo;
    private int capacidadMaxima;

    public ParqueAtracciones(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.semaforo = new Semaphore(capacidadMaxima); // Inicializa el semáforo con la capacidad
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void entrar() throws InterruptedException {
        String nombreHilo = Thread.currentThread().getName();

        System.out.println(nombreHilo + " quiere entrar. Permisos disponibles: " + semaforo.availablePermits());
        semaforo.acquire(); // Intenta adquirir un permiso (se bloqueará si no hay disponibles)
        System.out.println(nombreHilo + " ha ENTRADO al parque. Personas dentro: " +
                (capacidadMaxima - semaforo.availablePermits()));
    }

    public void salir() {
        String nombreHilo = Thread.currentThread().getName();
        semaforo.release(); // Libera un permiso
        System.out.println(nombreHilo + " ha SALIDO del parque. Personas dentro: " +
                (capacidadMaxima - semaforo.availablePermits()));
    }
}

class Visitante implements Runnable {
    private ParqueAtracciones parque;
    private String nombre;
    private Random random = new Random();

    public Visitante(ParqueAtracciones parque, String nombre) {
        this.parque = parque;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            parque.entrar();
            // Simular tiempo dentro del parque
            Thread.sleep(random.nextInt(2000) + 1000); // Entre 1 y 3 segundos
            parque.salir();
        } catch (InterruptedException e) {
            System.out.println(nombre + " fue interrumpido mientras estaba en el parque o esperando.");
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }
}