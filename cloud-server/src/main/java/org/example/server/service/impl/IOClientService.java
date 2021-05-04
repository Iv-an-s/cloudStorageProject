package org.example.server.service.impl;

import org.example.server.factory.Factory;
import org.example.server.service.ClientService;
import org.example.server.service.CommandDictionaryService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class IOClientService implements ClientService {

    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;
    private CommandDictionaryService dictionaryService;

    public IOClientService(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.dictionaryService = Factory.getCommandDirectoryService();
        initializeIOStreams();
    }

    private void initializeIOStreams() {
        try {
            this.in = new DataInputStream(clientSocket.getInputStream());
            this.out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startIOProcess() {
        new Thread(() -> {
            try{
                while (true) {
                    String clientCommand = readCommand();
                    //Обрабатываем команду
                    String commandResult = dictionaryService.processCommand(clientCommand);
                    writeCommandResult(commandResult);
                }
            }catch (Exception e){
                System.err.println("Client error: " + e.getMessage());
            }finally {
                closeConnection();
            }
        }).start();
    }

    public String readCommand() {
        try {
            return in.readUTF();
        } catch (IOException e) {
            throw new RuntimeException("readCommand exception " + e.getMessage());
        }
    }

    public void writeCommandResult(String commandResult){
        try {
            out.writeUTF(commandResult);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
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
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
