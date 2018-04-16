package com.newrelic.codingchallenge;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error when initializing connection");
        }
    }

    public void sendNumbers() {
        Random random = new Random();
        for (int i = 0; i < 400000; i++) {
            int num = ThreadLocalRandom.current().nextInt(1000000000);
            String formatted = String.format("%09d", num); //TODO: %09
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
            System.out.println("Error when closing");
        }
    }
}
