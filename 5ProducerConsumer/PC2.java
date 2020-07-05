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

    private final Lock rlock1 = new ReentrantLock();
    Condition condition1 = rlock1.newCondition();

    private final Lock rlock2 = new ReentrantLock();
    Condition condition2 = rlock2.newCondition();

    public void produces() throws InterruptedException {
        while (true) {
            if (list.size() < LIMIT) {
                rlock.lock();
                while (list.size() != LIMIT) {
                    this.val++;
                    System.out.println("T1 Adding val: " + val);
                    list.add(this.val);
                }
                rlock.unlock();
            } else {
                System.out.println("T1 List full, waiting ...");
                this.val = 0;

                rlock1.lock();
                condition1.signal();
                rlock1.unlock();

                rlock2.lock();
                condition2.await();
                rlock2.unlock();
            }
            Thread.sleep(500);
        }
    }

    public void consumes() throws InterruptedException {
        Thread.sleep(1000); // so that producer runs first
        while (true) {
            if (list.isEmpty()) {
                System.out.println("T2 Nothing to consume, so wait ...");

                rlock2.lock();
                condition2.signal();
                rlock2.unlock();

                rlock1.lock();
                condition1.await();
                rlock1.unlock();
            } else {
                rlock.lock();
                while (!list.isEmpty())
                    System.out.println("T2 Consume val: " + list.remove(0));
                rlock.unlock();
            }
            Thread.sleep(500);
        }
    }
}
