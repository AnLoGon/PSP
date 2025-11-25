package ud2.practices.cadenamontaje;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Códigos ANSI de colores en terminal para diferenciar cada hilo
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";

        final int PRODUCTOS = 5; // Número de productos a producir

        // Creación del monitor de la cadena de montaje
        CadenaMontaje monitor = new CadenaMontaje();

        // Creación de los runnables
        Fabricante fabricante = new Fabricante(monitor, PRODUCTOS);
        Ensamblador ensamblador = new Ensamblador(monitor, PRODUCTOS);
        Pintor pintor = new Pintor(monitor, PRODUCTOS);

        // Creación de los hilos
        Thread fabricar = new Thread(fabricante, ANSI_GREEN + "Fabricante" + ANSI_RESET);
        Thread ensamblar = new Thread(ensamblador, ANSI_RED + "Ensamblador" + ANSI_RESET);
        Thread pintar = new Thread(pintor, ANSI_BLUE + "Pintor" + ANSI_RESET);

        // Inicialización de los hilos
        fabricar.start();
        ensamblar.start();
        pintar.start();

        // Se espera a que terminen los hilos
        fabricar.join();
        ensamblar.join();
        pintar.join();

        System.out.println();
        System.out.println("*** Producción finalizada ***");
    }
}

// Monitor que controla el estado de las dos cintas transportadoras
class CadenaMontaje {
    // Estado de las cintas
    private boolean cinta1_tienePieza = false;
    private boolean cinta2_tienePieza = false;
    Random random = new Random();

    // El método "fabricar" crea la pieza y la deja en Cinta 1 si está libre
    public synchronized void fabricar(int producto) throws InterruptedException {
        // Espera si Cinta 1 ya tiene pieza
        while (cinta1_tienePieza) {
            System.out.println(Thread.currentThread().getName() + " -> Esperando a que Cinta 1 esté libre...");
            wait();
        }
        
        System.out.println(Thread.currentThread().getName() + " -> Creando pieza para producto nº" + producto + "...");
        Thread.sleep(random.nextInt(500, 2000)); // Simula el tiempo de fabricación (entre 0.5 y 2 segundos)
        cinta1_tienePieza = true;
        System.out.println(Thread.currentThread().getName() + " -> Pieza puesta en Cinta 1");

        // Avisa a los demás hilos que hay una nueva pieza en Cinta 1
        notifyAll();
    }

    // El método "ensamblar" coge la pieza de Cinta 1, la ensambla y la deposita en Cinta 2
    public synchronized void ensamblar(int producto) throws InterruptedException {
        // Espera si Cinta 1 está vacía o Cinta 2 está llena
        while (!cinta1_tienePieza || cinta2_tienePieza) {
            System.out.println(Thread.currentThread().getName() + " -> Esperando pieza en Cinta 1...");
            wait();
        }

        cinta1_tienePieza = false;
        System.out.println(Thread.currentThread().getName() + " -> Cogiendo pieza de Cinta 1. Ensamblando producto nº" + producto +  "...");
        Thread.sleep(random.nextInt(500, 2000));

        cinta2_tienePieza = true;
        System.out.println(Thread.currentThread().getName() + " -> Pieza ensamblada puesta en Cinta 2");

        // Avisa a los demás hilos que hay una nueva pieza en Cinta 2
        notifyAll();
    }

    // El método "pintar" coge la pieza de Cinta 2 y termina el producto
    public synchronized void pintar(int producto) throws InterruptedException {
        // Espera si Cinta 2 está vacía
        while (!cinta2_tienePieza) {
            System.out.println(Thread.currentThread().getName() + " -> Esperando pieza en Cinta 2...");
            wait();
        }

        cinta2_tienePieza = false;
        System.out.println(Thread.currentThread().getName() + " -> Cogiendo pieza de Cinta 2. Pintando producto...");
        Thread.sleep(random.nextInt(500, 2000));
        System.out.println(Thread.currentThread().getName() + " -> ¡Producto nº" + producto + " terminado!");

        // Avisa a los demás hilos que Cinta 2 está libre
        notifyAll();
    }
}

// La clase "Fabricante" fabrica los productos (por defecto 5)
class Fabricante implements Runnable {
    private final CadenaMontaje monitor;
    private final int productos;

    Fabricante(CadenaMontaje monitor, int productos) {
        this.monitor = monitor;
        this.productos = productos;
    }

    @Override
    public void run() {
        // Seguirá fabricando hasta completar el número de productos, después se interrumpirá
        try {
            for (int i = 1; i <= productos; i++) {
                monitor.fabricar(i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// La clase "Ensamblador" ensambla los productos
class Ensamblador implements Runnable {
    private final CadenaMontaje monitor;
    private final int productos;

    Ensamblador(CadenaMontaje monitor, int productos) {
        this.monitor = monitor;
        this.productos = productos;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= productos; i++) {
                monitor.ensamblar(i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// La clase "Pintor" pinta los productos
class Pintor implements Runnable {
    private final CadenaMontaje monitor;
    private final int productos;

    Pintor(CadenaMontaje monitor, int productos) {
        this.monitor = monitor;
        this.productos = productos;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= productos; i++) {
                monitor.pintar(i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}