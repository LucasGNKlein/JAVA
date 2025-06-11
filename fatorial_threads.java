import java.util.concurrent.*;

public class CalculoFatorial {
    public static void main(String[] args) {
        int[] numeros = {5, 6, 7, 8, 9};
        
        // a) Sem threads
        long startTime = System.currentTimeMillis();
        calcularFatorialSemThreads(numeros);
        long endTime = System.currentTimeMillis();
        System.out.println("Tempo sem threads: " + (endTime - startTime) + "ms");
        
        // b) Com 2 threads
        startTime = System.currentTimeMillis();
        calcularFatorialComThreads(numeros, 2);
        endTime = System.currentTimeMillis();
        System.out.println("Tempo com 2 threads: " + (endTime - startTime) + "ms");
        
        // c) Com 4 threads
        startTime = System.currentTimeMillis();
        calcularFatorialComThreads(numeros, 4);
        endTime = System.currentTimeMillis();
        System.out.println("Tempo com 4 threads: " + (endTime - startTime) + "ms");
    }

    private static void calcularFatorialSemThreads(int[] numeros) {
        for (int num : numeros) {
            long fatorial = calcularFatorial(num);
            System.out.println("Fatorial de " + num + " é " + fatorial);
        }
    }

    private static void calcularFatorialComThreads(int[] numeros, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int num : numeros) {
            executor.submit(() -> {
                long fatorial = calcularFatorial(num);
                System.out.println("Fatorial de " + num + " é " + fatorial);
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static long calcularFatorial(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * calcularFatorial(n - 1);
        }
    }
}
