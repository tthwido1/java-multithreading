package project.SLS;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SLSStudent implements Runnable{
    private final int id;
    private final SLSBook[] books;
    private final Set<Integer> booksRead;
    private final Random random;

    public SLSStudent(int id, SLSBook[] books) {
        this.id = id;
        this.books = books;
        this.booksRead = new HashSet<>();
        this.random = new Random();
    }

    @Override
    public void run() {
        while (this.booksRead.size() != this.books.length) {
            try {
                int bookId = random.nextInt(SLSConstants.NOF_BOOKS);
                this.booksRead.add(bookId);
                readBook(bookId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this + " finished reading all books");
    }

    private void readBook(int bookId) throws InterruptedException {
        this.books[bookId].readBook(this);
    }

    @Override
    public String toString() {
        return "Student " + this.id;
    }
}
