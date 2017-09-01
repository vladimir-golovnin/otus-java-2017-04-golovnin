package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageClient {
    private ClientChannel clientChannel;
    private final MessageProcessor messageProcessor;

    public MessageClient(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public void start(String host, int port){
        try {
            Socket socket = new Socket(host, port);
            clientChannel = new ClientChannel(socket);
            clientChannel.setOnMessageReceivedListener(messageProcessor);
            clientChannel.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        if(clientChannel != null){
            clientChannel.sendMessage(message);
        }
    }
}
