import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class FileGenerator implements Runnable{
    private final BlockingQueue <MyFile> queue;
    private final Random random = new Random();

    public FileGenerator(BlockingQueue<MyFile> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        String[] types = {"XML", "XLS", "JSON"};
        while (true){
            try {
                String type = types[random.nextInt(types.length)];
                int size = 10 + random.nextInt(91);
                MyFile file = new MyFile(type, size);

                queue.put(file);
                System.out.println("File added to queue");
                Thread.sleep(100 + random.nextInt(901));
            } catch (InterruptedException e) {
                System.out.println("Error");
                break;
            }
        }
    }
}
