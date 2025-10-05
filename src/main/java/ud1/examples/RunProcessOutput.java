package ud1.examples;

import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunProcessOutput {
    public static void main (String[] args) {
        //String[] program = {"wsl.exe", "echo", "Hello world!"};
        //String[] program = {"wsl.exe", "ls", "-l"}; // Muestra el directorio actual
        String[] program = {"wsl.exe", "rm", "fitxer.txt"}; // Elimina fitxer.txt que no existe
        ProcessBuilder pb = new ProcessBuilder(program);
        try {
            Process process = pb.start();
            // Objetos para poder leer la salida estándar y el error
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            int codiRetorn = process.waitFor();
            System.out.println("La ejecución de " + Arrays.toString(program) + " ha acabado con el código: " + codiRetorn);

            String line;
            System.out.println("Stdout:");
            while ((line = stdout.readLine()) != null)
                System.out.printf("    %s\n", line);

            System.out.println("Stderr:");
            while ((line = stderr.readLine()) != null)
                System.out.printf("    %s\n", line);

        } catch (IOException ex) {
            System.err.println("Excepción de E/S.");
            System.err.println(ex.getMessage());
            System.exit(-1);
        } catch (InterruptedException ex) {
            System.err.println("El proceso hijo ha finalizado de manera incorrecta.");
            System.exit(-1);
        }
    }
}