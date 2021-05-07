package com.studentlibrary;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Book {
    private int id;
    private Lock lock;

    private Random random;

    Book(int id){
        this.id=id;
        this.lock=new ReentrantLock();
        random=new Random();
    }

    public boolean read(Student student) throws InterruptedException {
        if (lock.tryLock()) {
            System.out.println(student + " is reading " + this);
            Thread.sleep(random.nextInt(Constants.READING_TIME));

            System.out.println(student + " finished reading book " + this);
            lock.unlock();
            return  true;
        } else
            return false;
    }



    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                '}';
    }
}
