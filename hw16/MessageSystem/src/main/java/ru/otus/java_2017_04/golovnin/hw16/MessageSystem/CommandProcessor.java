package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
    private final Map<String, Class<? extends Command>> commandMap = new HashMap<>();
    private final MessageSystem ms;
    private static final Gson gson = new Gson();

    public CommandProcessor(MessageSystem ms) {
        this.ms = ms;
    }

    public void addCommand(Class<? extends Command> classOfCmd){
        if(classOfCmd != null){
            commandMap.put(classOfCmd.getSimpleName(), classOfCmd);
        }
    }

    public void processCommand(String cmdName, String cmd, Address toAddress){
        if( cmdName != null && cmd != null && toAddress != null){
            Class<? extends Command> commandClass = commandMap.get(cmdName);
            if(commandClass != null) {
                Command commandObj = gson.fromJson(cmd, commandClass);
                if(commandObj != null) ms.sendMessage(commandObj, toAddress);
            }
        }
    }
}
