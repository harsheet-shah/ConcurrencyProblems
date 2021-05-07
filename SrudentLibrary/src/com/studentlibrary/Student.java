package com.studentlibrary;

import java.util.Random;

public class Student implements Runnable {
    private int id;
    private Book book[];
    private int readCount;
    private Random random;
    private boolean stop;

    Student(int id, Book[] book) {
        this.id = id;
        this.book = book;
        random = new Random();
    }

    @Override
    public void run() {

        while (!isStop()) {
            try {
                int bookId = random.nextInt(Constants.NUMBER_OF_BOOKS);
                if (book[bookId].read(this)) {
                    readCount++;
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public int getReadCount() {
        return readCount;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                '}';
    }
}
