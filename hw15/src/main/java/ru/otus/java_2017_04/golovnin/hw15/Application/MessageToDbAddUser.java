package ru.otus.java_2017_04.golovnin.hw15.Application;

import ru.otus.java_2017_04.golovnin.hw15.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw15.DbService.UserDataSet;
import ru.otus.java_2017_04.golovnin.hw15.Frontend.UserData;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;
import java.util.function.Consumer;

public class MessageToDbAddUser extends MessageToDbUpdateUser {
    private UserData user;

    public MessageToDbAddUser(Address from, UserData user) {
        super(from);
        this.user = user;
    }

    @Override
    protected Consumer<Addressee> changeUserData(DbService db) {
        db.saveUser(new UserDataSet(user.id, user.name, user.age));
        return null;
    }
}
