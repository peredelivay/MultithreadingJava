import java.net.Inet4Address;
import java.util.concurrent.BlockingQueue;

public class FileProcessor implements Runnable{
    private final String fileType;
    private final BlockingQueue<MyFile> queue;

    public FileProcessor(BlockingQueue<MyFile> queue, String type) {
        this.queue = queue;
        fileType = type;
        System.out.println(type + "processor up to work");
    }

    @Override
    public void run() {
        while(true) {
            try {
                MyFile file = queue.take();

                if (file.getType().equals(fileType)) {
                    System.out.println(fileType + " generator processing " + file.toString());
                    Thread.sleep(7 * file.getSize());
                    System.out.println(fileType + "generator finished processing " + file.toString());
                } else {
                    queue.put(file);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Error");
            }
        }
    }
}
