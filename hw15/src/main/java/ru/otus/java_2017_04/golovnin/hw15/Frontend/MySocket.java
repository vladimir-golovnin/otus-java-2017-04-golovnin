package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.java_2017_04.golovnin.hw15.Application.MessageToDbAddUser;
import ru.otus.java_2017_04.golovnin.hw15.Application.MessageToDbRemoveUser;
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
        UserData user1 = new UserData(100, "John", 30);
        UserData user2 = new UserData(200, "Jessica", 25);
        UserData user3 = new UserData(300, "Jason", 15);

        sendUsersData(Arrays.asList(user1, user2, user3));

        msAddress = ms.registerAddressee(this);
        if(msAddress == null) {
            session.close();
        }
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

    private void sendUsersData(List<UserData> users){
        if(session.isOpen()) {
            try {
                session.getRemote().sendString(jsonConverter.toJson(users));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String msg){
        if(session.isOpen()) {
            try {
                session.getRemote().sendString(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

