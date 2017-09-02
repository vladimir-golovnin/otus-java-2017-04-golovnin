package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MessageServer{
    private boolean isActive = true;
    private static final int WORKERS_NUM = 1;

    private final MessageProcessor messageProcessor;
    private final RouteTable routeTable;

    public MessageServer(MessageProcessor processor, RouteTable routeTable){
        this.messageProcessor = processor;
        this.routeTable = routeTable;
    }

    public void start(int port){
        new Thread(() -> {
            try(ServerSocket serverSocket = new ServerSocket(port)){

                while (isActive){
                    Socket clientSocket = serverSocket.accept();
                    ClientChannel clientChannel = new ClientChannel(clientSocket);
                    routeTable.addChannel(clientChannel);
                    clientChannel.setOnShutdownListener(routeTable::removeChannel);
                    clientChannel.setOnMessageReceivedListener(messageProcessor);
                    clientChannel.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
