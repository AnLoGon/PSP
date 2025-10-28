package ud2.practices.collectivetasks;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final String name;
    private final List<EmployeeThread> employees;
    private final List<Task> unfinishedTasks;
    private final List<Task> testingTasks;
    private final List<Task> finishedTasks;

    public Team(String name) {
        this.name = name;
        employees = new ArrayList<>();
        unfinishedTasks = new ArrayList<>();
        testingTasks = new ArrayList<>();
        finishedTasks = new ArrayList<>();
    }

    public void addEmployee(EmployeeThread employee) {
        this.employees.add(employee);
        employee.setTeam(this);
    }

    public void addTask(String taskName, int taskDuration) {
        unfinishedTasks.add(new Task(taskName, taskDuration));
    }

    public List<EmployeeThread> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }

    public synchronized Task getNextTask(){
        if(!unfinishedTasks.isEmpty()){
            return unfinishedTasks.removeFirst();
        }
        if(!testingTasks.isEmpty()){
            return testingTasks.removeFirst();
        }
        return null;
    }

    public synchronized void addTestingTask(Task t){
        testingTasks.add(t);
    }
    public synchronized void addFinishedTask(Task t){
        finishedTasks.add(t);
    }
}