import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PC2 { // fixed the BUG, use lock before condition.signal(), condition.wait()
    public static void main(String[] args) {
        PCLockHelper pc = new PCLockHelper();
        Thread thread1 = new Thread(() -> {
            try {
                pc.produces();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                pc.consumes();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long startTime = System.nanoTime();
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PCLockHelper {
    private final int LIMIT = 5;
    private int val = 0;
    private List<Integer> list = new ArrayList<>();

    private final Lock rlock = new ReentrantLock();
    Condition condition = rlock.newCondition();

    public void produces() throws InterruptedException {
        while (true) {
            if (list.size() < LIMIT) {
                rlock.lock();
                this.val++;
                System.out.println("T1 Adding val: " + val);
                list.add(this.val);
                rlock.unlock();
            } else {
                System.out.println("T1 List full, waiting ...");
                this.val = 0;
                rlock.lock();
                condition.signal();
                condition.await();
                rlock.unlock();
            }
            Thread.sleep(500);
        }
    }

    public void consumes() throws InterruptedException {
        Thread.sleep(1000); // so that producer runs first
        while (true) {
            if (list.isEmpty()) {
                System.out.println("T2 Nothing to consume, so wait ...");
                rlock.lock();
                condition.signal();
                condition.await();
                rlock.unlock();
            } else {
                rlock.lock();
                System.out.println("T2 Consume val: " + list.remove(0));
                rlock.unlock();
            }
            Thread.sleep(500);
        }
    }
}
