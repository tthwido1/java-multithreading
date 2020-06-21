package project.SLS;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SLSApp {
    public static void main(String[] args) {
        SLSStudent[] students = new SLSStudent[SLSConstants.NOF_STUDENTS];
        SLSBook[] books = new SLSBook[SLSConstants.NOF_BOOKS];

        IntStream.range(0, SLSConstants.NOF_BOOKS).forEach(id -> {
            books[id] = new SLSBook(id);
        });

        ExecutorService executorService = Executors.newFixedThreadPool(SLSConstants.NOF_STUDENTS);
        try {
            IntStream.range(0, SLSConstants.NOF_STUDENTS).forEach(id -> {
                students[id] = new SLSStudent(id, books);
                executorService.submit(students[id]);
            });
        } finally {
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Exit ...");
        }
    }
}
