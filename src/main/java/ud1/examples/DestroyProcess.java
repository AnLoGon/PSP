package ud1.examples;

import java.io.IOException;
import java.util.Arrays;

public class DestroyProcess {
    public static void main (String[] args) {
        // Indica el comando que utiliza este programa para iniciar un nuevo proceso
        String[] program = {"powershell", "sleep", "5"};

        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(program);

            System.out.println(
                    "El proceso: " + Arrays.toString(program)
                            + (process.isAlive() ? " está vivo." : " ha acabado.")
            );
            System.out.println("Destruint...");

            process.destroy();
            process.waitFor();

            System.out.println(
                    "El proceso: " + Arrays.toString(program)
                            + (process.isAlive() ? " está vivo." : " ha acabado.")
            );
        } catch(IOException ex) {
            System.err.println("Excepción de E/S.");
            System.err.println(ex.getMessage());
            System.exit(-1);
        } catch(InterruptedException ex) {
            System.err.println("El proceso hijo ha finalizado de manera incorrecta.");
            System.exit(-1);
        }
    }
}