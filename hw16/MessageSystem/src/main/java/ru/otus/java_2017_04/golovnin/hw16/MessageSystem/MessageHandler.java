package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import com.google.gson.JsonObject;

public interface MessageHandler {

    public void processMessage(JsonObject jsonObject, ClientChannel channel);

}
