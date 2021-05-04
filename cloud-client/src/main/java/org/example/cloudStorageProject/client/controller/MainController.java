package org.example.cloudStorageProject.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.cloudStorageProject.client.domain.Command;
import org.example.cloudStorageProject.client.factory.Factory;
import org.example.cloudStorageProject.client.service.NetworkService;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public TextField commandTextField;
    public TextArea commandResultTextArea;

    public NetworkService networkService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = Factory.getNetworkService();
        createCommandResultHandler();

    }

    private void createCommandResultHandler(){
        new Thread(()->{
            byte[] buffer = new byte[1024];
            while (true){
                int countBytes = networkService.readCommandResult(buffer);
                String resultCommand = new String(buffer, 0, countBytes);
                Platform.runLater(()->{
                    commandResultTextArea.appendText(resultCommand + System.lineSeparator());
                });
            }
        }).start();
    }

    public void sendCommand(ActionEvent actionEvent) {
        networkService.sendCommand(commandTextField.getText().trim().toLowerCase());
        commandTextField.clear();
    }

    public void shutdown(){
        networkService.closeConnection();
    }

}
