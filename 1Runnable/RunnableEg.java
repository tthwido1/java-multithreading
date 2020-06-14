import java.util.stream.IntStream;

public class RunnableEg {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            IntStream.range(0, 10).forEach(i -> System.out.println("R1: " + i));
        });

        Thread t2 = new Thread(() -> {
            IntStream.range(0, 10).forEach(i -> System.out.println("R2: " + i));
        });

        t1.start();
        t2.start();

    }

}