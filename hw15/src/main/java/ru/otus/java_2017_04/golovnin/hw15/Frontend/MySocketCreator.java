package ru.otus.java_2017_04.golovnin.hw15.Frontend;


import com.google.gson.Gson;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.MessageSystem;

public class MySocketCreator implements WebSocketCreator{

    private final MessageSystem ms;
    private final Gson jsonConverter = new Gson();

    public MySocketCreator(MessageSystem messageSystem) {
        this.ms = messageSystem;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        return new MySocket(ms, jsonConverter);
    }
}
