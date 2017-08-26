package ru.otus.java_2017_04.golovnin.hw15.Application;

import ru.otus.java_2017_04.golovnin.hw15.Frontend.AbstractMessageToFrontend;
import ru.otus.java_2017_04.golovnin.hw15.Frontend.MySocket;
import ru.otus.java_2017_04.golovnin.hw15.Frontend.UserData;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;

import java.util.List;
import java.util.function.Consumer;

public class MessageToFrontendAllUsers extends AbstractMessageToFrontend{
    private List<UserData> usersData;

    public MessageToFrontendAllUsers(Address from, List<UserData> users) {
        super(from);
        usersData = users;
    }

    @Override
    public Consumer<Addressee> execute(MySocket socket) {
        socket.sendUsersData(usersData);
        return null;
    }
}
