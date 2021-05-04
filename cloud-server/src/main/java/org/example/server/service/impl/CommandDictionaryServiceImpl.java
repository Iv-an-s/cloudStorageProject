package org.example.server.service.impl;

import org.example.server.factory.Factory;
import org.example.server.service.CommandDictionaryService;
import org.example.server.service.CommandService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandDictionaryServiceImpl implements CommandDictionaryService {

    private final Map<String, CommandService> commandDictionary;

    public CommandDictionaryServiceImpl(){
        commandDictionary = Collections.unmodifiableMap(getCommandDictionary());

    }

    private Map<String, CommandService> getCommandDictionary(){
        List<CommandService>commandServices = Factory.getCommandServices();
        Map<String, CommandService> commandDictionary = new HashMap<>();
        for (CommandService commandService : commandServices) {
            commandDictionary.put(commandService.getCommand(), commandService);
        }
        return commandDictionary;
    }

    @Override
    public String processCommand(String command) {
        String[]commandParts = command.split("\\s");
        if (commandParts.length>0 && commandDictionary.containsKey(commandParts[0].toLowerCase())) {
            return commandDictionary.get(commandParts[0]).processCommand(command);
        }else{
            return "Error: Unrecognised command";
        }
    }
}
