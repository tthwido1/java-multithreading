package project.SLS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SLSBook {
    private final int id;
    private final Lock rlock;

    public SLSBook(int id) {
        this.id = id;
        this.rlock = new ReentrantLock();
    }

    public void readBook(SLSStudent student) throws InterruptedException {
        System.out.println(student + " trying to read " + this);
        this.rlock.lock();
        System.out.println(student + " now reading " + this);
        Thread.sleep(1000);
        this.rlock.unlock();
        System.out.println(student + " finished reading " + this);
    }

    @Override
    public String toString() {
        return "Book " + this.id;
    }
}
