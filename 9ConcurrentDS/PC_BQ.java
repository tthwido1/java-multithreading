import java.util.concurrent.*;
import java.util.stream.IntStream;

public class PC_BQ {
    public static void main(String[] args) {
        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>();
        PCBQHelper pcbqHelper = new PCBQHelper(bq);
        Thread t1 = new Thread(() -> {
            IntStream.range(0,10).forEach(i -> pcbqHelper.produces());
        });
        Thread t2 = new Thread(() -> {
            IntStream.range(0,10).forEach(i -> pcbqHelper.consumes());
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PCBQHelper {
    private BlockingQueue<Integer> bq;
    private int val = 0;
    public PCBQHelper(BlockingQueue<Integer> bq) {
        this.bq = bq;
    }

    public void produces() {
        try {
            System.out.println("Produces: "+this.val);
            this.bq.put(this.val);
            Thread.sleep(Math.round((1+(10*Math.random()))*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.val++;
    }

    public void consumes() {
        try {
            System.out.println("Consumes: "+this.bq.take());
            Thread.sleep(Math.round((1+(10*Math.random()))*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}