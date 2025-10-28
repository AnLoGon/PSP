package ud2.practices.collectivetasks;

public class EmployeeThread extends Thread {
    Team team;
    public EmployeeThread(String name) {
        super();
        this.setName(name);
        this.team = null;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public void run() {
        while(true){
            Task t = team.getNextTask();
            if(t == null){
                System.out.printf("%s: No more pending tasks. Going home.\n", this.getName());
                break;
            }
            System.out.printf("%s: Assigned to task %s.\n", this.getName(), t.getName());
            if (t.status() == TaskStatus.UNFINISHED) {
                try {
                    t.work();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                team.addTestingTask(t);
            }
            if (t.status() == TaskStatus.TESTING) {
                try {
                    t.test();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                team.addFinishedTask(t);
            }
        }
        System.out.printf("%s: Ha realitzat totes les tasques assignades.\n", this.getName());
    }
}