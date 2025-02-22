import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[10000];
        int sum = 0;

        // init array
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 100);
        }

        long start_time = System.currentTimeMillis();
        // First Method
        for (int k = 0; k < array.length; k++) {
            sum += array[k];
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("First method time: " + (System.currentTimeMillis() - start_time));
        System.out.println("Sum = " + sum);

        //Second method Callable + ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(4);

        long start_time_m2 = System.currentTimeMillis();
        Future <Integer> part1 = executor.submit(new MyThread(array, 0, 2500));
        Future <Integer> part2 = executor.submit(new MyThread(array, 2500, 5000));
        Future <Integer> part3 = executor.submit(new MyThread(array, 5000, 7500));
        Future <Integer> part4 = executor.submit(new MyThread(array, 7500, 10000));

        int sum_m2 = 0;
        try {
            sum_m2 += part1.get();
            sum_m2 += part2.get();
            sum_m2 += part3.get();
            sum_m2 += part4.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        System.out.println("Second method time: " + (System.currentTimeMillis() - start_time_m2));
        System.out.println("Sum = " + sum_m2);

        //Third method ForkJoin

    }
}