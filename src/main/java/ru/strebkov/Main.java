package ru.strebkov;

import ru.strebkov.thread.ThreadClientHandler;
import ru.strebkov.thread.TreadManyClientTest;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        //server.start(); // когда extends Thread {
       Thread threadServer = new Thread(server);
       threadServer.start();
        try {
           // server.join(); // когда extends Thread {
          //  threadServer.join();
            Thread.sleep(100);
        } catch (InterruptedException e){
            return;
        }

//        System.out.println("Сервер готов");
//        Client client = new Client();
//        client.start();
//        System.out.println("Клиент подключен");

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 4; i++) {
            executorService.execute(new TreadManyClientTest());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
               // e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}

