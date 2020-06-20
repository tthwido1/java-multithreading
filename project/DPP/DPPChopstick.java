package project.DPP;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class DPPChopstick {
    private int id;
    private Lock rlock;

    public DPPChopstick(int id, Lock rlock) {
        this.id = id;
        this.rlock = rlock;
    }

    public boolean pickUp(DPPPhilosopher phil, DPPChopstickState chopstickState) {
        try {
            if (this.rlock.tryLock(10, TimeUnit.MILLISECONDS)) {
                this.rlock.lock();
                System.out.println(phil + " picked up " + chopstickState + " " + this);
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
