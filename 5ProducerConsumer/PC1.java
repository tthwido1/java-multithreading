import java.util.ArrayList;
import java.util.List;

public class PC1 {
    public static void main(String[] args) {
        PCHelper pc = new PCHelper();
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

class PCHelper {
    private final int LIMIT = 5;
    private int val = 0;
    private List<Integer> list = new ArrayList<>();

    private final Object pclock = new Object();

    public void produces() throws InterruptedException {
        synchronized (pclock) {
            while (true) {
                if (list.size() < LIMIT) {
                    this.val++;
                    System.out.println("T1 Adding val: " + val);
                    list.add(this.val);
                } else {
                    System.out.println("T1 List full, waiting ...");
                    this.val = 0;
                    pclock.notify();
                    pclock.wait();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consumes() throws InterruptedException {
        Thread.sleep(1000); // so that producer runs first
        synchronized (pclock) {
            while (true) {
                if (list.isEmpty()) {
                    System.out.println("T2 Nothing to consume, so wait ...");
                    pclock.notify();
                    pclock.wait();
                } else {
                    System.out.println("T2 Consume val: " + list.remove(0));
                }
                Thread.sleep(500);
            }
        }
    }
}