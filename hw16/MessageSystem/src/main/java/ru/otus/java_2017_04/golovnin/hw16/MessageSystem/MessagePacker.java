package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import com.google.gson.Gson;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.Message;

public class MessagePacker {
    private final MessageClient mc;
    private final Gson gson = new Gson();

    public MessagePacker(MessageClient messageClient){
        mc = messageClient;
    }

    public void sendMessage(Message msg){
        mc.sendMessage(gson.toJson(msg));
    }
}
