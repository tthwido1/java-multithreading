package project.DPP;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DPPChopstick {
    private final int id;
    private final Lock rlock;

    public DPPChopstick(int id) {
        this.id = id;
        this.rlock = new ReentrantLock();
    }

    public boolean pickUp(DPPPhilosopher phil, DPPChopstickState chopstickState) throws InterruptedException {
        if (this.rlock.tryLock(10, TimeUnit.MILLISECONDS)) {
            System.out.println(phil + " picked up " + chopstickState + " " + this);
            return true;
        }
        return false;
    }

    public void dropDown(DPPPhilosopher phil, DPPChopstickState chopstickState) {
        this.rlock.unlock();
        System.out.println(phil + " drop down " + chopstickState + " " + this);
    }

    @Override
    public String toString() {
        return "Chopstick " + this.id;
    }
}
