package com.newrelic.codingchallenge;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FiveSocketTest {

    private static final Integer PORT = 4000;

//    @BeforeClass
//    public static void start() throws InterruptedException {
//        Executors.newSingleThreadExecutor().submit(() -> new EchoMultiServer().start(PORT));
//        Thread.sleep(500);
//    }

    @Test
    public void givenClient1_whenServerResponds_thenCorrect() {
        Client client = new Client();
        client.startConnection("127.0.0.1", PORT);
        String msg1 = client.sendMessage("hello");
        String msg2 = client.sendMessage("world");
//        String terminate = client.sendMessage(".");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String terminate = client.sendMessage(".");
//        assertEquals(terminate, "bye");
//        client.stopConnection();
    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() {
        Client client = new Client();
        client.startConnection("127.0.0.1", PORT);
        String msg1 = client.sendMessage("hello");
        String msg2 = client.sendMessage("world");
//        String terminate = client.sendMessage(".");
        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
//        assertEquals(terminate, "bye");
//        client.stopConnection();
    }

    @Test
    public void givenClient3_whenServerResponds_thenCorrect() {
        Client client = new Client();
        client.startConnection("127.0.0.1", PORT);
        String msg1 = client.sendMessage("hello");
        String msg2 = client.sendMessage("world");
//        String terminate = client.sendMessage(".");
        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
//        assertEquals(terminate, "bye");
//        client.stopConnection();
    }

    @Test
    public void givenClient4_whenServerResponds_thenCorrect() {
        Client client = new Client();
        client.startConnection("127.0.0.1", PORT);
        String msg1 = client.sendMessage("hello");
        String msg2 = client.sendMessage("world");
//        String terminate = client.sendMessage(".");
        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
//        assertEquals(terminate, "bye");
//        client.stopConnection();
    }

    @Test
    public void givenClient5_whenServerResponds_thenCorrect() {
        Client client = new Client();
        client.startConnection("127.0.0.1", PORT);
        String msg1 = client.sendMessage("hello");
        String msg2 = client.sendMessage("world");
//        String terminate = client.sendMessage(".");
        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
//        assertEquals(terminate, "bye");
//        client.stopConnection();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String terminate = client.sendMessage(".");
//        assertEquals(terminate, "bye");
        client.stopConnection();
    }

    @Test
    public void givenClient6_whenServerResponds_thenCorrect() {
        Client client = new Client();
        client.startConnection("127.0.0.1", PORT);
        String msg1 = client.sendMessage("hello");
        String msg2 = client.sendMessage("world");
        String terminate = client.sendMessage(".");
        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
        client.stopConnection();
    }

    //Do I need Tear down?

}