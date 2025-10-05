package ud1.examples;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class RunPipelineColors {
    public static void main (String[] args) {

        ArrayList<ProcessBuilder> programs = new ArrayList<>();
        programs.add(new ProcessBuilder("wsl.exe", "sort", "files/ud1/colors.txt")); // Ordena el fichero
        programs.add(new ProcessBuilder("wsl.exe", "uniq", "-c")); // uniq -c muestra el número de repeticiones
        programs.add(new ProcessBuilder("wsl.exe", "sort", "-r")); // Ordena de manera inversa
        programs.add(new ProcessBuilder("wsl.exe", "head", "-3")); // Muestra las 3 primeras líneas

        try {
            List<Process> processes = ProcessBuilder.startPipeline(programs);

            // Esperamos a que acaben todos los procesos
            for(Process p : processes)
                p.waitFor();

            Process last = processes.get(processes.size() - 1); // Último proceso de la cadena
            BufferedReader stdout = new BufferedReader(new InputStreamReader(last.getInputStream()));

            String line;
            System.out.println("Stdout:");
            while ((line = stdout.readLine()) != null)
                System.out.printf("    %s\n", line);

        } catch (IOException ex) {
            System.err.println("Excepción de E/S.");
            System.out.println(ex.getMessage());
            System.exit(-1);
        } catch (InterruptedException ex) {
            System.err.println("El proceso hijo ha finalizado de manera incorrecta.");
            System.exit(-1);
        }
    }
}