package ud2.exercises.fibonacci;

public class FibonacciThread extends Thread {
    private int n;
    private int count;

    public FibonacciThread(int count, int n) {
        this.n = n;
        this.count = count;
    }

    @Override
    public void run() {
        long[] fibonacciNumbers = new long[n];

        if (n >= 1) {
            fibonacciNumbers[0] = 0;
            System.out.println("FIL" + count + ": Pas 1 de " + n + ": " + fibonacciNumbers[0]);
        }
        if (n >= 2) {
            fibonacciNumbers[1] = 1;
            System.out.println("FIL" + count + ": Pas 2 de " + n + ": " + fibonacciNumbers[1]);
        }

        for (int i = 2; i < n; i++) {
            fibonacciNumbers[i] = fibonacciNumbers[i - 1] + fibonacciNumbers[i - 2];
            System.out.println("FIL" + count + ": Pas " + (i + 1) + " de " + n + ": " + fibonacciNumbers[i]);
        }

        System.out.println("FIL" + count + ": ha acabat de calcular.");
    }
}