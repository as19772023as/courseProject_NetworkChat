package ru.strebkov.thread;

import ru.strebkov.logger.ServerLog;
import ru.strebkov.logger.ClientLog;

import java.io.*;
import java.net.Socket;

public class ThreadClientHandler implements Runnable {
    private final ClientLog clientLog;
    private static Socket clientDialog;
    ServerLog LOGGER;

    public ThreadClientHandler(Socket client, ServerLog LOGGER) {
        ThreadClientHandler.clientDialog = client;
        this.LOGGER = LOGGER;
        clientLog = ClientLog.getInstance();
    }

    @Override
    public void run() {
        try (PrintWriter prWr = new PrintWriter(clientDialog.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientDialog.getInputStream()))) {
            //Запись и чтение для приема и вывода создана
            System.out.println("Write and read for reception and output created");

            final String name = in.readLine() + "[" + clientDialog.getPort() + "]";
            LOGGER.log("New user ", String.format(": %s", name));

            while (!clientDialog.isClosed()) {
                final String msg = in.readLine();
                System.out.println("Read the message from " + name + "=>> " + msg);
                if (msg.equalsIgnoreCase("exit")) {
                    LOGGER.log("Exit from Chat", String.format(": %s", name));
                    prWr.println("Server is waiting - " + msg + " - ОК");
                    Thread.sleep(1000);
                    break;
                }
                //не получили выход, значит работаем
                System.out.println("Server is ready to record....");
                String msgAndUser = name + " =>> " + msg;
                prWr.println(">>>" + msgAndUser + " - ОК");
                clientLog.log(name, msg);
                System.out.println("Server recorded the message");
            }
            LOGGER.log("Closing a channel with a user", String.format(": [%s]- DONE", name));
        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }
    }
}
