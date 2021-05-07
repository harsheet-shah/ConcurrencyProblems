package com.tokenbucket;

import java.util.HashSet;
import java.util.Set;


public class TokenBucketSimulation {
    public static void main(String[] args) throws InterruptedException {

        Bucket bucket=new Bucket(2);
        Set<Thread> allThreads=new HashSet<>();
        for(int i=0;i<10;i++){
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bucket.getToken();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName(String.valueOf((i+1)));
            allThreads.add(thread);

        }
        for(Thread thread:allThreads)
            thread.start();
        for(Thread thread:allThreads)
            thread.join();


        System.out.println("Bye!");
    }
}
