package org.example.cloudStorageProject.client.service.impl;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import org.example.cloudStorageProject.client.domain.Command;
import org.example.cloudStorageProject.client.service.NetworkService;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class IONetworkService implements NetworkService {

    private static final String SERVER_HOST = "localhost";
    private static final int PORT = 8189;
    private static InputStream in;
    private static OutputStream out;

//    private static ObjectDecoderInputStream in; // с помощью этих классов-оберток будем передавать данные по сети, читать объекты,
//    private static ObjectEncoderOutputStream out; // с помощью средств сериализации/десериализации будут перегоняться из массива байт в объект

    private static IONetworkService instance;
    public static Socket socket;


    private IONetworkService(){}

    public static IONetworkService getInstance(){
        if(instance == null){
            instance = new IONetworkService();

            initializeSocket();
            initializeIOStreams();
        }
        return instance;
    }

    public static void initializeSocket() {
        try {
            socket = new Socket(SERVER_HOST, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initializeIOStreams() {
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
//            in = new ObjectDecoderInputStream(socket.getInputStream());
//            out = new ObjectEncoderOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendCommand(String command) {
//    public void sendCommand(Command command) {
        try {
            out.write(command.getBytes(StandardCharsets.UTF_8));
            out.flush();
//            out.writeObject(command);
//            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int readCommandResult(byte[] buffer) {
        try {
            return in.read(buffer);
        } catch (IOException e) {
            throw new RuntimeException("Read command result exception: " + e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
