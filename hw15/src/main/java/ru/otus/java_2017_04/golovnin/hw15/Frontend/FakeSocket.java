package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class FakeSocket {

    @OnWebSocketConnect
    public void onConnect(Session session){

    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){

    }

    @OnWebSocketMessage
    public void onMessage(String message){

    }
}
