
public class ThreadEg {
    public static void main(String[] args) {
        MyThread myThread1 = new MyThread("T1");
        MyThread myThread2 = new MyThread("T2");

        myThread1.start();
        myThread2.start();

        try {
            myThread1.join();
            myThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread extends Thread {
    private String threadName;

    MyThread(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; ++i) {
            try {
                System.out.println(this.threadName + ": " + i);
                this.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}