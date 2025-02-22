import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;

public class MyThread extends RecursiveTask<Integer> implements Callable <Integer>{
    int[] array;
    int start_ind;
    int end_ind;
    int sum = 0;

    public MyThread(int[] array, int start_ind, int end_ind) {
        this.array = array;
        this.start_ind = start_ind;
        this.end_ind = end_ind;
    }

    public MyThread(int[] array, int start_ind, int end_ind, int mid) {
        this.array = array;
        this.start_ind = start_ind;
        this.end_ind = end_ind;
    }

    @Override
    public Integer call() throws Exception{
        for (int i = start_ind; i < end_ind; i++) {
            sum += array[i];
            Thread.sleep(1);
        }
        return sum;
    }

    @Override
    protected Integer compute() {
        if (end_ind - start_ind <= 1000) {
            for (int i = start_ind; i < end_ind; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            int mid = (start_ind + end_ind) / 2;
            MyThread left = new MyThread(array, start_ind, mid);
            MyThread right = new MyThread(array, mid, end_ind);
            left.fork();
            return right.compute() + left.join();
        }
    }
}
