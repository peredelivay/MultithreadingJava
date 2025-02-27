import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose task (1,2,3):");
        System.out.println("Task 1 - Three methods of execution\nTask 2 - User input\nTask 3 - File generator\n");
        int opt = scan.nextInt();
        switch (opt) {
            case 1:
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
                        Thread.sleep(1);
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
                ForkJoinPool pool = new ForkJoinPool();

                MyThread task = new MyThread(array, 0, array.length);

                long start_time_m3 = System.currentTimeMillis();

                int sum_m3 = pool.invoke(task);

                System.out.println("Third method time: " + (System.currentTimeMillis() - start_time_m3));
                System.out.println("Sum = " + sum_m3);
                break;
            case 2:
                System.out.println("Print 0 if you want to finish program");
                Scanner scan_ex2 = new Scanner(System.in);
                ExecutorService executor_ex2 = Executors.newCachedThreadPool();

                while(true) {
                    int num;

                    num = scan_ex2.nextInt();
                    if (num == 0) {
                        break;
                    }
                    Future<Integer> future = executor_ex2.submit(()->{
                        int delay = ThreadLocalRandom.current().nextInt(1,6);
                        System.out.println("Working on " + num + ". Delay is " + delay);
                        try {
                            Thread.sleep(delay * 1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return num * num;
                    });

                    executor_ex2.submit(() -> {
                       try {
                           System.out.printf("Result %d\n", future.get());
                       } catch (InterruptedException | ExecutionException e){
                           System.out.println("Error");
                       }
                    });
                }
                executor_ex2.shutdown();
                System.out.println("Program finished");
                break;
            case 3:
                BlockingQueue <MyFile> queue = new LinkedBlockingQueue<MyFile>(5);

                ExecutorService executor_ex3 = Executors.newCachedThreadPool();

                executor_ex3.submit(new FileGenerator(queue));

                executor_ex3.submit(new FileProcessor(queue, "XML"));
                executor_ex3.submit(new FileProcessor(queue, "XLS"));
                executor_ex3.submit(new FileProcessor(queue, "JSON"));

                executor_ex3.shutdown();
                System.out.println("Program finished");
                break;
            default:
                System.out.println("No such task");
                break;
        }
    }
}