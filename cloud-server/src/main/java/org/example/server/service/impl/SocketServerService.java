package org.example.server.service.impl;

import org.example.server.factory.Factory;
import org.example.server.service.ServerService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerService implements ServerService {

    private static final int SERVER_PORT = 8189;
    private static SocketServerService instance;

    private SocketServerService(){}

    public static SocketServerService getInstance(){
        if(instance == null){
            instance = new SocketServerService();

        }
        return instance;
    }

    @Override
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            System.out.println("Server started on port " + SERVER_PORT);
            while (true){
                Socket clientSocket = serverSocket.accept();
                Factory.getClientService(clientSocket).startIOProcess();
                System.out.println("New client is connected");
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Server has finished the job");
    }

}
