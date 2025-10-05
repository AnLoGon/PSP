package ud1.exercises;

import java.io.IOException;
import java.util.ArrayList;

public class LaunchApplications {
    public static void main(String[] args) {
        ArrayList<String[]> applications = new ArrayList<>();
        // Todas aquellas aplicaciones que no estén en la variable de entorno $PATH
        // deben ser especificadas en la ruta al programa
        applications.add(new String[]{"C:\\Program Files\\Mozilla Firefox\\firefox.exe"});
        applications.add(new String[]{"C:\\Users\\alg17\\AppData\\Local\\Programs\\Microsoft VS Code\\Code.exe"});

        ArrayList<Process> processes = new ArrayList<>();

        for(String[] app : applications){
            ProcessBuilder pb = new ProcessBuilder(app);
            try {
                Process p = pb.start();
                processes.add(p);

                p.waitFor();
            } catch (IOException | InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}