package ru.otus.java_2017_04.golovnin.hw15.Application;

import ru.otus.java_2017_04.golovnin.hw15.Frontend.UserData;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Message;

import java.util.List;
import java.util.function.Consumer;

public class MessageToFrontendAllUsers extends Message{
    private List<UserData> usersData;

    public MessageToFrontendAllUsers(Address from, List<UserData> users) {
        super(from);
        usersData = users;
    }

    @Override
    public Consumer<Addressee> execute(Object obj) {
        if(obj instanceof MySocketCreator){
            MySocketCreator notificator = (MySocketCreator)obj;
            notificator.sendUsersData(usersData);
        }
        return null;
    }
}
