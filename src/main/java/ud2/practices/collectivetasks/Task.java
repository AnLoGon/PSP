package ud2.practices.collectivetasks;

public class Task {
    private final int duration;
    private final String name;
    private TaskStatus status;

    public Task(String name, int duration) {
        this.name = name;
        this.duration = duration;
        status = TaskStatus.UNFINISHED;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public TaskStatus status() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void work() throws InterruptedException {
        Thread current = Thread.currentThread();
        System.out.printf("%s: Starting task %s...\n", current.getName(), this.name);

        Thread.sleep(this.duration);

        setStatus(TaskStatus.TESTING);
        System.out.printf("%s: Finished task %s (%d).\n", current.getName(), this.name, this.duration);
    }

    public void test() throws InterruptedException {
        Thread current = Thread.currentThread();
        System.out.printf("%s: Testing task %s...\n", current.getName(), this.name);

        Thread.sleep(this.duration / 2);

        setStatus(TaskStatus.FINISHED);
        System.out.printf("%s: Finished task %s (%d).\n", current.getName(), this.name, this.duration / 2);
    }
}