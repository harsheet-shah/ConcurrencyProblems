package com.uberride;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UberSeat {
    private int democrats = 0;
    private int republican = 0;

    private Semaphore democratWaiting = new Semaphore(0);
    private Semaphore republicWaiting = new Semaphore(0);

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
        @Override
        public void run() {
            System.out.println("All members arrived...");
        }
    });
    private Lock lock = new ReentrantLock();
    private int noOfDemocrats = 0, noOfrepublic = 0;

    public void seatDemocrat() throws InterruptedException, BrokenBarrierException {
        boolean ride = false;

        if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
            democrats++;
            if (democrats == 4) {
                democratWaiting.release(3);
                democrats -= 4;
                noOfDemocrats = 4;
                ride = true;
            } else if (democrats == 2 && republican == 2) {
                democratWaiting.release(1);
                republicWaiting.release(2);
                ride = true;
                democrats -= 2;
                republican -= 2;
                noOfDemocrats = 2;
                noOfrepublic = 2;
            } else {
                lock.unlock();
                democratWaiting.acquire();
            }

        }

        seated();
        cyclicBarrier.await();

        if (ride) {
            drive();
            lock.unlock();
        }
    }

    public void seatRepublic() throws InterruptedException, BrokenBarrierException {
        boolean ride = false;

        if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
            republican++;
            if (republican == 4) {
                republicWaiting.release(3);
                republican -= 4;
                noOfrepublic = 4;
                ride = true;
            } else if (democrats == 2 && republican == 2) {
                democratWaiting.release(2);
                republicWaiting.release(1);
                ride = true;
                democrats -= 2;
                republican -= 2;
                noOfDemocrats = 2;
                noOfrepublic = 2;
            } else {
                lock.unlock();
                republicWaiting.acquire();
            }
        }
        seated();
        cyclicBarrier.await();

        if (ride) {
            drive();
            lock.unlock();
        }
    }


    private void drive() {
        System.out.println("Uber ride with " + noOfDemocrats + " Democrats and " + noOfrepublic + " Republicans");
        //clear for next ride
        noOfrepublic = 0;
        noOfDemocrats = 0;
    }

    private void seated() {
        System.out.println(Thread.currentThread().getName() + " seated...");
    }

    public void run() throws InterruptedException {
        Set<Thread> allMember = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        seatDemocrat();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("Democrat_" + (i + 1));
            allMember.add(thread);
        }

        for (int i = 0; i < 14; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        seatRepublic();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("Republic_" + (i + 1));
            allMember.add(thread);
        }

        for (Thread t : allMember)
            t.start();
        for (Thread t : allMember)
            t.join();

        System.out.println("Bye!");
    }

}
