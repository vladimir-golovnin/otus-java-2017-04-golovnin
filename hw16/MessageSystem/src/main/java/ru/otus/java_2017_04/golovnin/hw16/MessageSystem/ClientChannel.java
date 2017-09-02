package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientChannel {
    private final Socket socket;
    private ClientChannelOnShutdownListener onShutdownListener;
    private MessageProcessor onMessageReceivedListener;

    private static final int WORKERS_NUM = 2;
    private static final int END_CHAR = 2;
    private final ExecutorService executorService = Executors.newFixedThreadPool(WORKERS_NUM);

    private final BlockingQueue<String> outgoingMessages = new LinkedBlockingQueue<>();

    public ClientChannel(Socket socket){
        this.socket = socket;
    }

    public void start(){
        executorService.execute(this::receiveMessages);
        executorService.execute(this::processOutgoingMessages);
    }

    private void receiveMessages(){
        if(!socket.isClosed()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                StringBuilder messageBuilder = new StringBuilder();
                int ch;
                while (!socket.isClosed() && socket.isConnected()) {
                    switch (ch = reader.read()) {
                        case -1:
                            break;
                        case END_CHAR:
                            String receivedMessage = messageBuilder.toString();
                            onMessageReceivedListener.processMessage(receivedMessage, this);
                            int builderSize = messageBuilder.length();
                            messageBuilder = new StringBuilder(builderSize);
                            break;
                        default:
                            messageBuilder.append((char) ch);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception!");
            } finally {
                this.shutdown();
            }
        }else shutdown();
    }

    private void processOutgoingMessages(){
        if(!socket.isClosed() && socket.isConnected()) {
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
                while (!socket.isClosed() && socket.isConnected()) {
                    String outgoingMessage = outgoingMessages.take();
                    writer.print(outgoingMessage);
                    writer.print((char)END_CHAR);
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.shutdown();
            }
        }else shutdown();
    }

    public void sendMessage(String message){
        if(message != null) outgoingMessages.offer(message);
    }

    void setOnMessageReceivedListener(MessageProcessor listener){
        this.onMessageReceivedListener = listener;
    }

    public void setOnShutdownListener(ClientChannelOnShutdownListener listener){
        onShutdownListener = listener;
    }

    public void shutdown(){
        executorService.shutdown();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(onShutdownListener != null) onShutdownListener.inform(this);
        System.out.println("Channel is closed");
    }
}
