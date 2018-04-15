package com.newrelic.codingchallenge;

import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class FiveClientServer {
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            // this is spawning Handler threads; this needs to be maxxed at 5 but dynamic,
            // moves back down to 4 and allows one more. Try that!
            while (true)
                executorService.submit(new FiveClientHandler(serverSocket.accept()));
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
        private PrintWriter out;
        private BufferedReader in;

        // Constructs a handler thread; stores away a socket
        public FiveClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                // this will read your numbers and terminate your client not your app. this should happen if the 9 digits does not qualify
                //
                while (true) {
                    inputLine = in.readLine();
                    if (inputLine == null || inputLine.equals(".")) {
                        out.println("bye");
                        break;
                    }
                    // do things to the input here. you are probably not going to need an out since you have a one way
                    // communication
                    out.println(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // this closes the client. for system shut down do something else.
                try {
                    in.close();
                    out.close();
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
        FiveClientServer server = new FiveClientServer();
        server.start(4000);
    }
}