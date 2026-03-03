package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public CommandLineRunner startAdminServer() {
        return args -> {
            Thread adminThread = new Thread(() -> {
                try (ServerSocket serverSocket = new ServerSocket(8888)) {
                    System.out.println("[Admin Monitor] Listening on port 8888 (Thread ID: " + Thread.currentThread().getId() + ")");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();

                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                        String input = in.readLine();
                        System.out.println("   [Admin Monitor] Received command: " + input);

                        out.println("SERVER SAYS: I received '" + input + "' - Status: OK");

                        clientSocket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            adminThread.start();
        };
    }
}