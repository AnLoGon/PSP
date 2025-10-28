package ud2.exercises.tasks;

public class ManagerThread extends Thread{
    private Team team;

    public ManagerThread(Team team) {
        super();
        this.team = team;
        this.setName(String.format("%sManager", team.getName()));
    }

    @Override
    public void run() {
        // TODO: Make all your assigned employees do their tasks
        for (EmployeeThread employee : team.getEmployees()) {
            employee.start();
        }

        for (EmployeeThread employee : team.getEmployees()) {
            try {
                employee.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("%s: L'equip %s ha realitzat totes les tasques.\n", this.getName(), team.getName());
    }
}