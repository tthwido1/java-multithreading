import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Latch {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        IntStream.range(0, 5).forEach(id -> {
            executorService.submit(() -> {
                Worker worker = new Worker(id, countDownLatch);
                worker.doWork();
            });
        });

        try {
            countDownLatch.await(); // wait till all the threads have finished their tasks
            System.out.println("All tasks finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}

class Worker {
    private final int id;
    private final CountDownLatch latch;
    public Worker(int id, CountDownLatch countDownLatch) {
        this.latch = countDownLatch;
        this.id = id;
    }

    public void doWork() {
        work();
        latch.countDown(); // decrement the count once task is done
    }

    private void work() {
        System.out.println("id: " + id + " Do some work ...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}