package com.diningphilosopher;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopsticks {
    private int id;
    private Lock lock;

    Chopsticks(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }

    public boolean pickUp(Philosopher philosopher, States state) throws InterruptedException {
        // here we deal with deadlock
        if (lock.tryLock(10, TimeUnit.MILLISECONDS)) { // returns true if it is available to acquire lock.
            System.out.println(philosopher + " picked up " + state.toString() + " " + this);
            return true;
        }
        /*
        Note instead of tryLock() alternatively we can use lock.lock()
        can get rid of if/else
         */
        return false;
    }

    public void putDown(Philosopher philosopher, States state) {
        lock.unlock();
        System.out.println(philosopher + " put down " + state.toString() + " " + this);
    }

    @Override
    public String toString() {
        return "Chopsticks{" +
                "id=" + id +
                '}';
    }
}
