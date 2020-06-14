public class NotSynchronized1 {
    private static volatile double counter = 0;

    private static void incrementCounter(String threadName) { // make this method synchronized, so that only 1 thread can access it at a time
        System.out.println(threadName + " before: "+ counter);
        counter++;  // Non-atomic operation on volatile field 'counter'
        System.out.println(threadName + " after: "+ counter);
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 50; ++i) {
                incrementCounter("T1");
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 50; ++i) {
                incrementCounter("T2");
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
            System.out.println("counter: " + counter); // counter should be 100 but its value is <100
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
