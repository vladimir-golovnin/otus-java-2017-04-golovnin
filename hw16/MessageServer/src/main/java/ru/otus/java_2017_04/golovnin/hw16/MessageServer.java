package ru.otus.java_2017_04.golovnin.hw16;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MessageServer{
    private boolean isActive = true;
    private static final int WORKERS_NUM = 1;
    private final List<ClientChannel> clientChannels = Collections.synchronizedList(new ArrayList<>());
    private final MessageProcessor messageProcessor;

    public MessageServer(MessageProcessor processor){
        this.messageProcessor = processor;
    }

    public void start(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while (isActive){
                Socket clientSocket = serverSocket.accept();
                ClientChannel clientChannel = new ClientChannel(clientSocket);
                clientChannels.add(clientChannel);
                clientChannel.setOnShutdownListener(() -> clientChannels.remove(clientChannel));
                clientChannel.setOnMessageReceivedListener(messageProcessor);
                clientChannel.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
