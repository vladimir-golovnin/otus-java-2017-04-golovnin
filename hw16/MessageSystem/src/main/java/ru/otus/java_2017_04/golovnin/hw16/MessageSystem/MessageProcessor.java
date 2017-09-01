package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class MessageProcessor {
    private final Map<String, MessageHandler> handlers = new HashMap<>();
    private final JsonParser parser = new JsonParser();

    public void setHandler(String type, MessageHandler handler){
        handlers.put(type, handler);
    }

    public void processMessage(String message, ClientChannel source) {
            JsonObject jsonObject = (JsonObject)parser.parse(message);
            String messageType = jsonObject.get(Message.TYPE_FIELD).getAsString();
            handlers.get(messageType).processMessage(jsonObject, source);
    }
}
