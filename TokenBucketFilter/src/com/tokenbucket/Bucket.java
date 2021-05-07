package com.tokenbucket;

import java.util.Random;

public class Bucket  {
    private int maxToken;
    private long lastRequest = System.currentTimeMillis();
    private boolean stop;
    private Random random;
    private long possibleToken;

    Bucket(int maxToken) {
        this.maxToken = maxToken;
        random = new Random();
    }



    public synchronized void getToken() throws InterruptedException {

        possibleToken += (System.currentTimeMillis() - lastRequest)%100;
        //System.out.println("token "+possibleToken);
        if (possibleToken > maxToken) {
            possibleToken = maxToken;
        } else if (possibleToken == 0) {
            Thread.sleep(1000);
        } else
            possibleToken--;

        lastRequest = System.currentTimeMillis();
        System.out.println("Thread " + Thread.currentThread().getId()+ " has generated token at " + lastRequest%10);


    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
