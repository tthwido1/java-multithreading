public class Synchronized1_1 {
    private static volatile double counter1 = 0;
    private static volatile double counter2 = 0;

    private static void incrementCounter1(String threadName) {
        synchronized (Synchronized1_1.class) { // class level intrinsic lock
            System.out.println(threadName + " before counter1: "+ counter1);
            counter1++;
            System.out.println(threadName + " after counter1: "+ counter1);
        }
    }
    private static void incrementCounter2(String threadName) {
        synchronized (Synchronized1_1.class) { // class level intrinsic lock
            System.out.println(threadName + " before counter2: "+ counter2);
            counter2++;
            System.out.println(threadName + " after counter2: "+ counter2);
        }
    }

    private static void compute(String threadName) {
        for (int i = 0; i < 50; ++i) {
            incrementCounter1(threadName);
            incrementCounter2(threadName);
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> compute("T1"));
        Thread thread2 = new Thread(() -> compute("T2"));

        long startTime = System.nanoTime();
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
            long stopTime = System.nanoTime();
            System.out.println("counter1: " + counter1);
            System.out.println("counter2: " + counter2);
            System.out.println("time: " + (stopTime - startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
