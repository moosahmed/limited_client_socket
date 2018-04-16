package com.newrelic.codingchallenge;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

public class Queuer implements Runnable{
    private BST<String> stringBST = new BST<>();
    private BlockingQueue<String> blockingQueue;
    private Timer timer;

    public Queuer(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        timer = new Timer();
        timer.schedule(new Summary(stringBST), 0, 10000);
    }

    @Override
    public void run() {
        while(true){
            try {
                String buffer = blockingQueue.take();
                stringBST.put(buffer);
            } catch (InterruptedException e) {
                System.out.println("The connection was interrupted");
            }
        }
    }

    class Summary extends TimerTask {
        private BST<String> BST;
        private int pre_uniq = 0;
        private int pre_duplicate = 0;
        private int uniq_diff;
        private int dup_diff;
        public Summary(BST<String> BST) {
            this.BST = BST;
        }

        @Override
        public void run() {
            uniq_diff = BST.size() - pre_uniq;
            dup_diff = BST.getDuplicates() - pre_duplicate;
            pre_uniq = BST.size();
            pre_duplicate = BST.getDuplicates();
            System.out.printf("Received %d unique numbers,%d duplicates. Unique total: %d\n",
                    uniq_diff, dup_diff, BST.size());
        }
    }
}