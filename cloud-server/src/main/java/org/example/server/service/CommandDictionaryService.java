package org.example.server.service;

public interface CommandDictionaryService {

    /**
     * принимаем команды
     * находит реализацию
     *
     */
    String processCommand(String command);
}
