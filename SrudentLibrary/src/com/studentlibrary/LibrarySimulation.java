package com.studentlibrary;

import com.sun.org.apache.bcel.internal.Const;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LibrarySimulation {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService =null;
        Student student[]=null;
        Book book[];
        Random random=new Random();

        try {
            book=new Book[Constants.NUMBER_OF_BOOKS];
            student=new Student[Constants.NUMBER_OF_STUDENTS];
            executorService= Executors.newFixedThreadPool(Constants.NUMBER_OF_STUDENTS);

            for(int i=0;i<Constants.NUMBER_OF_BOOKS;i++)
                book[i]=new Book(i);

            for(int i=0;i<Constants.NUMBER_OF_STUDENTS;i++)
            {
                student[i]=new Student(i,book);
                executorService.execute(student[i]);
            }
            Thread.sleep(5000);
            for(int i=0;i<Constants.NUMBER_OF_STUDENTS;i++)
                student[i].setStop(true);

        }finally {
                executorService.shutdown();
                while(!executorService.isTerminated())
                    Thread.sleep(1000);

                for(Student students:student){
                    System.out.println(students+" read books# "+students.getReadCount());
                }
        }
    }
}
