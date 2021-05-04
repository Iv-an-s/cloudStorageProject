package org.example.server.factory;

import org.example.server.service.ClientService;
import org.example.server.service.CommandDictionaryService;
import org.example.server.service.CommandService;
import org.example.server.service.ServerService;
import org.example.server.service.impl.CommandDictionaryServiceImpl;
import org.example.server.service.impl.IOClientService;
import org.example.server.service.impl.NettyServerService;
import org.example.server.service.impl.SocketServerService;
import org.example.server.service.impl.command.ViewFilesInDirCommand;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Factory {

//    public static ServerService getServerService(){
//        return SocketServerService.getInstance();
//    }

    public static ServerService getServerService(){
        return new NettyServerService();
    }

    public static ClientService getClientService(Socket clientSocket){
        return new IOClientService(clientSocket);
    }

    public static CommandDictionaryService getCommandDirectoryService(){
        return new CommandDictionaryServiceImpl();
    }

    public static List<CommandService> getCommandServices(){
        return Arrays.asList(new ViewFilesInDirCommand()); //todo
    }

}
