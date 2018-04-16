package com.newrelic.codingchallenge;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Client {
    //    private static final Logger LOG = LoggerFactory.getLogger(EchoClient.class);

    private Socket clientSocket;
    private PrintWriter out;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
//            LOG.debug("Error when initializing connection", e);
            System.out.println("Error when initializing connection");
        }

    }

    public void sendNumbers() {
        Random random = new Random();
        for (int i = 0; i < random.nextInt(100); i++) {
            int num = ThreadLocalRandom.current().nextInt(1000000000); // check ints instead of strings, is leading 0 ints even possible
            String formatted = String.format("%08d", num); //TODO: %09
            System.out.println("sending: " + formatted);
            out.println(formatted);
        }
    }

    public void terminate() {
        out.println("terminate");
    }

    public void stopConnection() {
        try {
            out.close();
            clientSocket.close();
        } catch (IOException e) {
//            LOG.debug("error when closing", e);
            System.out.println("Error when closing");
        }
    }
}
