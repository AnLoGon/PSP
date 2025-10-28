package ud2.practices.swimmingpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SwimmingPool {
    private final int poolCapacity;
    private final int showersCapacity;

    // TODO: Add semaphores
    private final Semaphore poolSemaphore;
    private final Semaphore showersSemaphore;

    public SwimmingPool(int poolCapacity, int showersCapacity) {
        this.poolCapacity = poolCapacity;
        this.showersCapacity = showersCapacity;

        // TODO: Create semaphores
        poolSemaphore = new Semaphore(poolCapacity);
        showersSemaphore = new Semaphore(showersCapacity);
    }

    // TODO: create get() method for semaphores
    public Semaphore getPoolSemaphore() {
        return poolSemaphore;
    }
    public Semaphore getShowersSemaphore() {
        return showersSemaphore;
    }

    public static void main(String[] args) {
        SwimmingPool pool = new SwimmingPool(10, 3);
        String[] names = {
            "Andrès", "Àngel", "Anna", "Carles", "Enric",
            "Helena", "Isabel", "Joan", "Lorena", "Mar",
            "Maria", "Marta", "Míriam", "Nicolàs", "Òscar",
            "Paula", "Pere", "Teresa", "Toni", "Vicent"
        };
        List<PersonThread> persons = new ArrayList<>();
        for(String name : names) {
            // TODO: Create the threads and start them
            PersonThread person = new PersonThread(name, pool);
            persons.add(person);
            person.start();
        }

        // TODO: Wait 60 seconds and kick all persons out
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (PersonThread person : persons) {
            person.interrupt();
        }

        // TODO: Wait for all persons to leave
        for(PersonThread person : persons) {
            try {
                person.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Tothom ha marxat de la piscina.");
    }
}