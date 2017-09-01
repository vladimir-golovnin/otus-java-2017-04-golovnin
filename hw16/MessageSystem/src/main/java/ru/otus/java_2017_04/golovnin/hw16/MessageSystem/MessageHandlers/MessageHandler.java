package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers;


import com.google.gson.JsonObject;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ClientChannel;

public interface MessageHandler {

    public void processMessage(JsonObject jsonObject, ClientChannel channel);

}
