package com.newrelic.codingchallenge;

import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable{
    private BST<String> stringBST = new BST<>();
    private BlockingQueue<String> blockingQueue;
    public WriterThread(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                String buffer = blockingQueue.take();
                stringBST.put(buffer);
                System.out.println(stringBST.size());
                System.out.println(stringBST.getDuplicates());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
