public class WaitNotify {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
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

class ProducerConsumer {
    public void produces() throws InterruptedException {
        synchronized (this) {
            System.out.println("T1 produces");
            wait();
            System.out.println("T1 exit");
        }
    }

    public void consumes() throws InterruptedException {
        Thread.sleep(1000); // so that producer runs first
        synchronized (this) {
            System.out.println("T2 consumes");
            notify(); // Thread1 resumes only after this method execution is over and not immediately after notify()
            Thread.sleep(1000);
            System.out.println("T2 exit");
        }
        Thread.sleep(2000);
        System.out.println("T2 exit consumes()");
    }
}