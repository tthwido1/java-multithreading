import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CyclicBarriers {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            System.out.println("All threads finished their tasks");
        });

        IntStream.range(0, 5).forEach(id -> {
            executorService.submit(() -> {
                CBWorker worker = new CBWorker(id, barrier);
                worker.doWork();
            });
        });
        executorService.shutdown();
    }
}

class CBWorker {
    private final int id;
    private final CyclicBarrier barrier;
    public CBWorker(int id, CyclicBarrier barrier) {
        this.barrier = barrier;
        this.id = id;
    }

    public void doWork() {
        System.out.println("Thread with ID " + id + " starts the task...");
        work();
        System.out.println("Thread with ID " + id + " finished...");
        try {
            barrier.await();
            System.out.println("Thread with ID " + id + " After await...");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
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