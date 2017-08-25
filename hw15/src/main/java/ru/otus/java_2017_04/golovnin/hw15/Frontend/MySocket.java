package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.java_2017_04.golovnin.hw15.DbService.MessageToDbGetUser;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.MessageSystem;

import java.io.IOException;

@WebSocket
public class MySocket {

    private Session session;
    private final MessageSystem ms;
    private final Gson jsonConverter;
    private Address msAddress;

    public MySocket(MessageSystem messageSystem, Gson jsonConverter){
        this.ms = messageSystem;
        this.jsonConverter = jsonConverter;
    }

    @OnWebSocketConnect
    public void onConnect(Session session){
        this.session = session;
        msAddress = ms.registeraddressee(this);
        if(msAddress == null) {
            sendMessage(WebsocketMessage.TYPE_ERROR, null);
            session.close();
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        ms.deregister(msAddress);
    }

    @OnWebSocketMessage
    public void onMessage(String message){
        UserAction action = jsonConverter.fromJson(message, UserAction.class);
        ms.sendMessage(new MessageToDbGetUser(msAddress, action.getId()), "Data base");
    }

    public void sendUserData(FrontendUserData data){
        sendMessage(WebsocketMessage.TYPE_USER_DATA, data);
    }

    private void sendMessage(int type, Object content){
        if(session.isOpen()) {
            try {
                session.getRemote().sendString(jsonConverter.toJson(new WebsocketMessage(type, content)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

