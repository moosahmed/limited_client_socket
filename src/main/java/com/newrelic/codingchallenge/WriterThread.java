package com.newrelic.codingchallenge;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable{
    private BST<String> stringBST = new BST<>();
    private BlockingQueue<String> blockingQueue;
    public WriterThread(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    private Timer timer = new Timer();
//    timer.schedule(new Summary(stringBST), 0, 10000);
//    public void summary() {
//        int pre_uniq = stringBST.size();
//        int pre_duplicate = stringBST.getDuplicates();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.printf("Recieved %d unique numbers,%d duplicates. Unique total: %d\n",
//                stringBST.size() - pre_uniq, stringBST.getDuplicates() - pre_duplicate, stringBST.size());
//    }
    class Summary extends TimerTask {
        private BST<String> BST;
        public Summary(BST<String> BST) {
            this.BST = BST;
            int pre_uniq = BST.size();
            int pre_duplicate = BST.getDuplicates();
        }
        private int pre_uniq;
        private int pre_duplicate;
        @Override
        public void run() {
            System.out.printf("Recieved %d unique numbers,%d duplicates. Unique total: %d\n",
                    BST.size() - pre_uniq, BST.getDuplicates() - pre_duplicate, BST.size());
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                String buffer = blockingQueue.take();
                stringBST.put(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}