package ru.otus.java_2017_04.golovnin.hw15.Application;

import ru.otus.java_2017_04.golovnin.hw15.DbService.AbstractMessageToDb;
import ru.otus.java_2017_04.golovnin.hw15.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw15.DbService.UserDataSet;
import ru.otus.java_2017_04.golovnin.hw15.Frontend.UserData;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class MessageToDbUpdateUser extends AbstractMessageToDb{

    public MessageToDbUpdateUser(Address from) {
        super(from);
    }

    @Override
    public Consumer<Addressee> execute(DbService dbService) {
        Consumer<Addressee> consumer = changeUserData(dbService);
        List<UserDataSet> userDataSetList = dbService.getAllUsers();
        List<UserData> users = new LinkedList<>();
        userDataSetList.forEach(
                user -> users.add(
                        new UserData( user.getId(), user.getName(), user.getAge() )
                )
        );

        return (addressee) ->
        {
            if(consumer != null) consumer.accept(addressee);
            addressee.getMessageSystem().sendMessage(
                    new MessageToFrontendAllUsers(addressee.getAddress(), users),
                    getFromAddress());
        };
    }

    protected Consumer<Addressee> changeUserData(DbService db)
    {
        return null;
    }
}
