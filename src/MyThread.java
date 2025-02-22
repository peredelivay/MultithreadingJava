import java.util.concurrent.Callable;

public class MyThread implements Callable <Integer>{
    int[] array;
    int start_ind;
    int end_ind;
    int sum = 0;

    public MyThread(int[] array, int start_ind, int end_ind) {
        this.array = array;
        this.start_ind = start_ind;
        this.end_ind = end_ind;
    }

    @Override
    public Integer call() throws Exception{
        for (int i = start_ind; i < end_ind; i++) {
            sum += array[i];
            Thread.sleep(1000);
        }
        return sum;
    }
}
