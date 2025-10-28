package ud2.exercises.restaurant;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Restaurant {

    public static void main(String[] args) {
        RestaurantSimulation simulation = new RestaurantSimulation();
        simulation.start();
    }
}

class RestaurantSimulation {
    private final int MAX_PREPARED_PLATES = 5;
    private final int NUM_CLIENTS = 4;
    private final int NUM_COOKS = 2;
    private final int NUM_WAITERS = 1;

    private final Kitchen kitchen = new Kitchen(MAX_PREPARED_PLATES);
    private final Queue<Client> waitingClients = new LinkedList<>();

    public void start() {
        // Crear y arrancar los camareros
        for (int i = 0; i < NUM_WAITERS; i++) {
            new Waiter("Camarero-" + i, waitingClients, kitchen).start();
        }

        // Crear y arrancar los cocineros
        for (int i = 0; i < NUM_COOKS; i++) {
            new Cook("Cocinero-" + i, kitchen).start();
        }

        // Crear y arrancar los clientes
        for (int i = 0; i < NUM_CLIENTS; i++) {
            new Client("Cliente-" + i, waitingClients).start();
        }
    }
}

// Cliente
class Client extends Thread {
    private static final String[] MENU = {"Pizza", "Pasta", "Ensalada", "Hamburguesa", "Sopa"};
    private final Queue<Client> waitingQueue;
    private String currentOrder;

    public Client(String name, Queue<Client> waitingQueue) {
        super(name);
        this.waitingQueue = waitingQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Elegir un plato aleatoriamente
                currentOrder = MENU[new Random().nextInt(MENU.length)];
                synchronized (waitingQueue) {
                    System.out.println(getName() + " está esperando para pedir: " + currentOrder);
                    waitingQueue.add(this);
                    waitingQueue.notifyAll(); // Notificar a los camareros
                }
                synchronized (this) {
                    wait(); // Esperar a ser atendido
                }
                System.out.println(getName() + " está disfrutando de su " + currentOrder);
                Thread.sleep(2000); // Simula el tiempo para comer
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getCurrentOrder() {
        return currentOrder;
    }
}

// Camarero
class Waiter extends Thread {
    private final Queue<Client> waitingQueue;
    private final Kitchen kitchen;

    public Waiter(String name, Queue<Client> waitingQueue, Kitchen kitchen) {
        super(name);
        this.waitingQueue = waitingQueue;
        this.kitchen = kitchen;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Client client;
                synchronized (waitingQueue) {
                    while (waitingQueue.isEmpty()) {
                        waitingQueue.wait(); // Esperar hasta que haya un cliente
                    }
                    client = waitingQueue.poll();
                }
                System.out.println(getName() + " está atendiendo a " + client.getName());
                String order = client.getCurrentOrder();
                kitchen.placeOrder(order);
                synchronized (client) {
                    client.notify(); // Notificar al cliente que su pedido fue entregado
                }
                System.out.println(getName() + " ha servido el pedido a " + client.getName());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Cocinero
class Cook extends Thread {
    private final Kitchen kitchen;

    public Cook(String name, Kitchen kitchen) {
        super(name);
        this.kitchen = kitchen;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String order = kitchen.takeOrder();
                System.out.println(getName() + " está preparando " + order);
                Thread.sleep(3000); // Simula el tiempo de preparación del plato
                kitchen.completeOrder(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Cocina
class Kitchen {
    private final int maxPreparedPlates;
    private final Queue<String> pendingOrders = new LinkedList<>();
    private int preparedPlates = 0;

    public Kitchen(int maxPreparedPlates) {
        this.maxPreparedPlates = maxPreparedPlates;
    }

    public synchronized void placeOrder(String order) throws InterruptedException {
        pendingOrders.add(order);
        System.out.println("Cocina ha recibido un pedido: " + order);
        notifyAll(); // Notificar a los cocineros
    }

    public synchronized String takeOrder() throws InterruptedException {
        while (pendingOrders.isEmpty() || preparedPlates >= maxPreparedPlates) {
            wait(); // Esperar hasta que haya pedidos pendientes y espacio para preparar
        }
        String order = pendingOrders.poll();
        notifyAll(); // Notificar que se puede tomar otro pedido
        return order;
    }

    public synchronized void completeOrder(String order) throws InterruptedException {
        while (preparedPlates >= maxPreparedPlates) {
            wait(); // Esperar hasta que haya espacio para almacenar el plato preparado
        }
        preparedPlates++;
        System.out.println("Cocina ha preparado el pedido: " + order + ". Platos listos: " + preparedPlates);
        notifyAll(); // Notificar a los camareros que hay platos listos
    }

    public synchronized void deliverPlate() throws InterruptedException {
        while (preparedPlates == 0) {
            wait(); // Esperar hasta que haya platos listos
        }
        preparedPlates--;
        System.out.println("Un plato ha sido servido. Platos restantes: " + preparedPlates);
        notifyAll(); // Notificar que se puede preparar más
    }
}
