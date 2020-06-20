package project.DPP;

import java.util.Random;

public class DPPPhilosopher implements Runnable{
    private final int id;
    private final DPPChopstick leftChopstick;
    private final DPPChopstick rightChopstick;
    private int eatingCounter;
    private volatile boolean isFull;
    private final Random random;

    public DPPPhilosopher(int id, DPPChopstick leftChopstick, DPPChopstick rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.eatingCounter = 0;
        this.isFull = false;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (!isFull) {
                think();
                if (leftChopstick.pickUp(this, DPPChopstickState.LEFT)) {
                    if (rightChopstick.pickUp(this, DPPChopstickState.RIGHT)) {
                        eat();
                        rightChopstick.dropDown(this, DPPChopstickState.RIGHT);
                    }
                    leftChopstick.dropDown(this, DPPChopstickState.LEFT);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void eat() throws InterruptedException {
        System.out.println(this + " eating ...");
        this.eatingCounter++;
        Thread.sleep(getRandomTime());
    }

    private void think() throws InterruptedException {
        System.out.println(this + " thinking ...");
        Thread.sleep(getRandomTime());
    }

    public int getEatingCounter() {
        return this.eatingCounter;
    }

    public void setIsFull() {
        this.isFull = true;
    }

    private int getRandomTime() {
        return this.random.nextInt(1000);
    }

    @Override
    public String toString() {
        return "Philosopher " + this.id;
    }
}
