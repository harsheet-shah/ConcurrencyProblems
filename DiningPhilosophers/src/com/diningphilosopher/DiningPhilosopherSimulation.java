package com.diningphilosopher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosopherSimulation {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = null;
        Philosopher philosopher[] = null;
        Chopsticks chopsticks[];

        try {
            chopsticks = new Chopsticks[Constants.NUMBER_OF_CHOPSTICKS];
            philosopher = new Philosopher[Constants.NUMBER_OF_PHILOSOPHER];
            executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHER);

            for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; i++) {
                chopsticks[i] = new Chopsticks(i);
            }

            for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHER; i++) {
                //left =i   right =(i+1)%n
                philosopher[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICKS]);
                executorService.execute(philosopher[i]);
            }

            Thread.sleep(Constants.SIMULATION_TIME);
            for (Philosopher philosophers : philosopher) {
                philosophers.setFull(true);
            }

        } finally {
            executorService.shutdown();

            while (!executorService.isTerminated())
                Thread.sleep(1000);

            for (Philosopher philosophers : philosopher) {
                System.out.println(philosophers + " eat# " + philosophers.getEatingCounter());
            }
        }
    }
}
