package ru.otus.java_2017_04.golovnin.hw16.Commands;

import ru.otus.java_2017_04.golovnin.hw16.Frontend.MySocketCreator;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.UserData;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Addressee;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Command;

import java.util.List;
import java.util.function.Consumer;

public class CommandToFrontendAllUsers extends Command {
    private List<UserData> usersData;

    public CommandToFrontendAllUsers(Address from, List<UserData> users) {
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
