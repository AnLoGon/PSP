package ud2.practices.collectivetasks;

public class CollectiveTasks {
    public static void main(String[] args) {
        Team frontend = new Team("Frontend");
        frontend.addTask("Main page design", 2500);
        frontend.addTask("Shopping cart navigation bar", 5500);
        frontend.addTask("Breadcrumb", 1500);
        frontend.addTask("Profile page", 7500);
        frontend.addEmployee(new EmployeeThread("Pere"));
        frontend.addEmployee(new EmployeeThread("Maria"));

        Team backend = new Team("Backend");
        backend.addTask("Connect API to database", 3000);
        backend.addTask("Shopping API models", 4200);
        backend.addTask("API authentication", 2100);
        backend.addTask("Testing API", 5500);
        backend.addEmployee(new EmployeeThread("Anna"));
        backend.addEmployee(new EmployeeThread("Arnau"));

        Team database = new Team("Database");
        database.addTask("Relation Model", 5000);
        database.addTask("Define models in the database", 4200);
        database.addTask("Install DBMS", 3000);
        database.addTask("Views", 2800);
        database.addEmployee(new EmployeeThread("Mireia"));
        database.addEmployee(new EmployeeThread("Mar"));

        for(EmployeeThread e : frontend.getEmployees()){
            e.start();
        }
        for(EmployeeThread e : backend.getEmployees()){
            e.start();
        }
        for(EmployeeThread e : database.getEmployees()){
            e.start();
        }
        for(EmployeeThread e : frontend.getEmployees()){
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        for(EmployeeThread e : backend.getEmployees()){
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        for(EmployeeThread e : database.getEmployees()){
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

        System.out.println("Projecte acabat! Tots els equips han acabat les tasques assignades.");
    }
}