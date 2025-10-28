package ud2.examples.pitstop;

public class RaiseMechanic extends Mechanic {
    public RaiseMechanic(Car car) {
        super(car);
    }

    @Override
    public void run()  {
        try {
            this.car.raise();
            System.out.println("¡Coche levantado!");
            this.car.release();
            System.out.println("¡Coche bajado!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}