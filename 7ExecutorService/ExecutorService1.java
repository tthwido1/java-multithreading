
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorService1 {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		System.out.println("### Inside main " + Thread.currentThread().getName() + " ###");
		for (int i = 0; i < 10; ++i) {
			System.out.println("### task: " + i + " ###");
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("### Inside run " + Thread.currentThread().getName() +" ###");
				}
			});
		}

		executorService.shutdown();
		System.out.println("### Exiting main ###");
	}

}