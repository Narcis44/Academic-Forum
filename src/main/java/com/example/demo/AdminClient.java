package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AdminClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8888;

        System.out.println("Connecting to Admin Server on port " + port + "...");

        try (Socket socket = new Socket(host, port)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("CHECK_STATUS");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();

            System.out.println(response);

        } catch (Exception e) {
            System.out.println("Could not connect. Is the main app running?");
        }
    }
}