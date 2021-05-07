package com.diningphilosopher;

import java.util.Random;

public class Philosopher implements Runnable {
    // Every philosopher is going to be different thread.
    private int id;
    private Chopsticks leftChopSticks;
    private Chopsticks rightChopSticks;
    private int eatingCounter;
    private volatile boolean full=false;
    private Random random;

    Philosopher(int id, Chopsticks leftChopSticks, Chopsticks rightChopSticks) {
        this.id = id;
        this.leftChopSticks = leftChopSticks;
        this.rightChopSticks = rightChopSticks;
        random = new Random();
    }

    @Override
    public void run() {

        try {
            while (!full) {

                think();
                if (leftChopSticks.pickUp(this, States.LEFT)) {
                    //able to acquire leftChopstick
                    if (rightChopSticks.pickUp(this, States.RIGHT)) {
                        //able to acquire rightChopstick
                        eat();
                        rightChopSticks.putDown(this, States.RIGHT);
                    }
                    leftChopSticks.putDown(this, States.RIGHT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void think() throws InterruptedException {
        System.out.println(this + " is thinking...");
        Thread.sleep(random.nextInt(1000));
    }

    public void eat() throws InterruptedException {
        System.out.println(this + " is eating..");
        eatingCounter++;
        Thread.sleep(random.nextInt(1000));
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public boolean isFull() {
        return this.full;
    }

    public int getEatingCounter() {
        return eatingCounter;
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                '}';
    }
}
