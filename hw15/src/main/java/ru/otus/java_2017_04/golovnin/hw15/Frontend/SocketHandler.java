package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.MessageSystem;

public class SocketHandler extends WebSocketServlet {

    private MessageSystem ms;

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        //webSocketServletFactory.setCreator(new MySocketCreator(ms));
        webSocketServletFactory.register(FakeSocket.class);
    }

}
