package ru.otus.java_2017_04.golovnin.hw16.Application;

import ru.otus.java_2017_04.golovnin.hw16.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw16.DbService.UserDataSet;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.UserData;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Addressee;
import java.util.function.Consumer;

public class CommandToDbAddUser extends CommandToDbUpdateUser {
    private UserData user;

    public CommandToDbAddUser(Address from, UserData user) {
        super(from);
        this.user = user;
    }

    @Override
    protected Consumer<Addressee> changeUserData(DbService db) {
        db.saveUser(new UserDataSet(user.id, user.name, user.age));
        return null;
    }
}
