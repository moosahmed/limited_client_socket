package com.newrelic.codingchallenge;


import java.io.*;
import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

public class FiveClientServer {
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    protected BlockingQueue<String> queue;
    private WriterThread writer;


    public FiveClientServer(BlockingQueue<String> blockingQueue) {
        this.queue = blockingQueue;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            writer = new WriterThread(queue);
            new Thread(writer).start();
            // this is spawning Handler threads; this needs to be maxxed at 5 but dynamic,
            // moves back down to 4 and allows one more. Try that!
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
        // Do I need this here for full system stop, full system stop if anybody says termiante.
        // System.exit(0)
    }

    private static class FiveClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private BlockingQueue<String> blockingQueue;

        private Pattern nineDigit = Pattern.compile("\\d{9}");

        // Constructs a handler thread; stores away a socket
        private FiveClientHandler(Socket socket, BlockingQueue<String> blockingQueue) {
            this.clientSocket = socket;
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                // this will read your numbers and terminate your client not your app.
                while (true) {
                    inputLine = in.readLine();
                    if (inputLine == null || !(nineDigit.matcher(inputLine).matches())) {
                        break;
                    }
                    blockingQueue.put(inputLine);
//                    // TODO: What Happens if client doesn't close connection?
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                // this closes the client. for system shut down do something else.
                try {
                    in.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // closing message or whatever here
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting up server ....");
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
        FiveClientServer server = new FiveClientServer(queue);
        server.start(4000);
    }
}
