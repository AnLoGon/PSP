package ud1.examples;

import java.io.IOException;
import java.util.Arrays;

public class RunProcess {
    public static void main (String[] args) {
        // Indica el comando que utiliza este programa para iniciar un nuevo proceso
        String[] program = {"notepad"};

        ProcessBuilder pb = new ProcessBuilder(program);
        try {
            // Inicia el proceso hijo
            System.out.printf("Se ha iniciado el proceso %s.\n", Arrays.toString(program));
            Process process = pb.start();

            // El proceso Java (padre) espera a que el proceso hijo finalice
            int codiRetorn = process.waitFor();
            System.out.println("La ejecución de " + Arrays.toString(program) + " devuelve " + codiRetorn);
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