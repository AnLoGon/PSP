package ud2.examples.pitstop;

public class TireMechanic extends Mechanic {
    private Tire tire;

    public TireMechanic(Car car, Tire tire) {
        super(car);
        this.tire = tire;
    }

    @Override
    public void run() {
        try {
            this.car.replaceTire(tire);
            System.out.printf("Neumático %s reemplazado.\n", tire);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}