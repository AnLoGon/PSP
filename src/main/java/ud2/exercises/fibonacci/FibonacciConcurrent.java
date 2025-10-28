package ud2.exercises.fibonacci;

import java.util.Scanner;

public class FibonacciConcurrent {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueix els nombres a calcular:");
        int cont = 1;

        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }
            else if (n > 0) {
                FibonacciThread ft = new FibonacciThread(cont++, n);
                ft.start();
            }
        }
        System.out.println("Programa acabat.");
    }
}