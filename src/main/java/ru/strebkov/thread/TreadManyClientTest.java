package ru.strebkov.thread;

import java.io.*;
import java.net.Socket;

import ru.strebkov.logger.*;

public class TreadManyClientTest implements Runnable {

    static Socket socket;

    public TreadManyClientTest() {
        try {
            socket = new Socket("127.0.0.1", 7879);
            System.out.println("Client connected - start");
            Thread.sleep(100);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (PrintWriter prWr = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("The client has connected");
            System.out.println("\nEnter your name to join the server!\n" +
                    "Then enter messages to send to users or 'exit' to exit the channel: TEST");

            String name = Thread.currentThread().getName();
            prWr.println(name);

            Thread.sleep(1000);
            ThreadReadMessage send = new ThreadReadMessage();
            send.start();
            String msg;
            for (int i = 1; i <= 5; i++) {
                msg = "Message - " + i;
                prWr.println(msg);

                Thread.sleep(1000);
                if (in.read() > -1) {
                    msgFromServer(in);
                }
            }
            msg = "exit";
            prWr.println(msg);

            Thread.sleep(1000);
            if (msg.equalsIgnoreCase("exit")) {
                System.out.println("Client kill connections  TEST");
                send.interrupt();
            }
            System.out.println("Closing the connection channel - DONE. TEST");
        } catch (IOException | InterruptedException e) {
           // e.printStackTrace();

        }
    }

    private void msgFromServer(BufferedReader in) throws IOException {
        String msgServ = in.readLine();
    }
}
