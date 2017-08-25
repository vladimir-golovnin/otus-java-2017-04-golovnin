package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.MessageSystem;

public class SocketHandler extends WebSocketHandler {

    private final MessageSystem ms;

    public SocketHandler(MessageSystem messageSystem){
        this.ms = messageSystem;
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.setCreator(new MySocketCreator(ms));
    }


}
