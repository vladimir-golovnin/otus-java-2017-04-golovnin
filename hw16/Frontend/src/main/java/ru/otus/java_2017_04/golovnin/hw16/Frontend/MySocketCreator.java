package ru.otus.java_2017_04.golovnin.hw16.Frontend;


import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbAddUser;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbRemoveUser;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbUpdateUser;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageSystem;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MySocketCreator implements WebSocketCreator, ClientsNotificator{

    private final MessageSystem ms;
    private final Gson jsonConverter = new Gson();
    private final Set<Session> sessionSet = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public MySocketCreator(MessageSystem messageSystem) {
        ms = messageSystem;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        return new MySocket();
    }

    @Override
    public void sendUsersData(List<UserData> users){
        sessionSet.forEach(session -> {
                    if (session.isOpen()) {
                        try {
                            session.getRemote().sendString(jsonConverter.toJson(users));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @WebSocket
    public class MySocket {

        private Session session;
        private Address msAddress;

        @OnWebSocketConnect
        public void onConnect(Session session){
            this.session = session;
            sessionSet.add(session);

            msAddress = ms.registerAddressee(this);
            if(msAddress == null) {
                session.close();
            }
            else ms.sendMessage(
                    new CommandToDbUpdateUser(msAddress),
                    "Data base");
        }

        @OnWebSocketClose
        public void onClose(int statusCode, String reason){
            ms.deregister(msAddress);
            sessionSet.remove(session);
        }

        @OnWebSocketMessage
        public void onMessage(String message){
            UserActionMessage decodedMessage = jsonConverter.fromJson(message, UserActionMessage.class);
            UserData user = decodedMessage.user;
            switch (decodedMessage.action){
                case UserActionMessage.ACTION_ADD:
                    ms.sendMessage(new CommandToDbAddUser(msAddress, user), "Data base");
                    break;
                case UserActionMessage.ACTION_REMOVE:
                    ms.sendMessage(new CommandToDbRemoveUser(msAddress, user.id), "Data base");
                    break;
            }
        }
    }
}
