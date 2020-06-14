import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Sem1 {
    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; ++i) {
            executor.execute(Sem1Helper::downloadFile);
        }
    }
}

class Sem1Helper {
    private static Semaphore sem1 = new Semaphore(3, true);
    // only 3 threads are allowed to access the resources acquired under this semaphore

    public static void downloadFile() {
        try {
            sem1.acquire();
            download();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sem1.release();
        }
    }

    private static void download() {
        System.out.println("Downloading ...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
