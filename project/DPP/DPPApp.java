package project.DPP;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class DPPApp {
    public static void main(String[] args) {
        DPPPhilosopher[] philosophers = new DPPPhilosopher[DPPConstants.NOF_PHIL];
        DPPChopstick[] chopsticks = new DPPChopstick[DPPConstants.NOF_CHOPS];

        IntStream.range(0, DPPConstants.NOF_CHOPS).forEach(id -> {
            chopsticks[id] = new DPPChopstick(id);
        });

        ExecutorService executorService = Executors.newFixedThreadPool(DPPConstants.NOF_PHIL);
        try {
            IntStream.range(0, DPPConstants.NOF_PHIL).forEach(id -> {
                philosophers[id] = new DPPPhilosopher
                        (id, chopsticks[id], chopsticks[(id + 1) % DPPConstants.NOF_CHOPS]);
                executorService.submit(philosophers[id]);
            });
            Thread.sleep(DPPConstants.SIMULATION_TIME_IN_MILLIS);
            IntStream.range(0, DPPConstants.NOF_PHIL).forEach(id -> {
               philosophers[id].setIsFull();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            IntStream.range(0, DPPConstants.NOF_PHIL).forEach(id -> {
                System.out.println(id + " " + philosophers[id].getEatingCounter());
            });
        }
    }
}
