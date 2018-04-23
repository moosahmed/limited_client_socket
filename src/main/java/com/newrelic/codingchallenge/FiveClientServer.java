package com.newrelic.codingchallenge;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class FiveClientServer {
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private BlockingQueue<Integer> queue;

    // blockingQueue used to keep process thread safe.
    public FiveClientServer(BlockingQueue<Integer> blockingQueue) {
        this.queue = blockingQueue;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            Queuer queuer = new Queuer(queue);
            new Thread(queuer).start();
            while (true) executorService.submit(new FiveClientHandler(serverSocket.accept(), queue));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class FiveClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private BlockingQueue<Integer> blockingQueue;
        private Pattern nineDigit = Pattern.compile("\\d{9}");
        private int inputInt;

        // Constructs a handler thread; stores away a socket and sets up the queue
        private FiveClientHandler(Socket socket, BlockingQueue<Integer> blockingQueue) {
            this.clientSocket = socket;
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while (true) {
                    inputLine = in.readLine();
                    if (inputLine.equals("terminate")) System.exit(0);
                    if (inputLine == null || !(nineDigit.matcher(inputLine).matches())) {
                        break;
                    }
                    inputInt = Integer.parseInt(inputLine);
                    blockingQueue.put(inputInt);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting up server ....");
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2000000);
        FiveClientServer server = new FiveClientServer(queue);
        server.start(4000);
    }
}
