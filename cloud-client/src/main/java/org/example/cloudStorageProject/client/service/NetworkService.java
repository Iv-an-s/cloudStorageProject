package org.example.cloudStorageProject.client.service;

import org.example.cloudStorageProject.client.domain.Command;

public interface NetworkService {

//    void sendCommand(Command command);

    void sendCommand(String command);

//    Command readCommandResult();

    int readCommandResult(byte[] buffer);

    void closeConnection();

}
