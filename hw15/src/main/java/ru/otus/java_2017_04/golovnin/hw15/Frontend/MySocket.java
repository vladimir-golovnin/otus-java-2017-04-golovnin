package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.java_2017_04.golovnin.hw15.Application.MessageToDbAddUser;
import ru.otus.java_2017_04.golovnin.hw15.Application.MessageToDbRemoveUser;
import ru.otus.java_2017_04.golovnin.hw15.Application.MessageToDbUpdateUser;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.MessageSystem;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

        msAddress = ms.registerAddressee(this);
        if(msAddress == null) {
            session.close();
        }
        else ms.sendMessage(
                new MessageToDbUpdateUser(msAddress),
                "Data base");
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        ms.deregister(msAddress);
    }

    @OnWebSocketMessage
    public void onMessage(String message){
        UserActionMessage decodedMessage = jsonConverter.fromJson(message, UserActionMessage.class);
        UserData user = decodedMessage.user;
        switch (decodedMessage.action){
            case UserActionMessage.ACTION_ADD:
                ms.sendMessage(new MessageToDbAddUser(msAddress, user), "Data base");
                break;
            case UserActionMessage.ACTION_REMOVE:
                ms.sendMessage(new MessageToDbRemoveUser(msAddress, user.id), "Data base");
                break;
        }
    }

    public void sendUsersData(List<UserData> users){
        if(session.isOpen()) {
            try {
                session.getRemote().sendString(jsonConverter.toJson(users));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

