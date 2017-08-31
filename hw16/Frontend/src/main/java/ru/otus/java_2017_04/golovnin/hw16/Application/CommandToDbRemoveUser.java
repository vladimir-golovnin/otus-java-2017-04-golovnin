package ru.otus.java_2017_04.golovnin.hw16.Application;

import ru.otus.java_2017_04.golovnin.hw16.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Addressee;
import java.util.function.Consumer;

public class CommandToDbRemoveUser extends CommandToDbUpdateUser {
    private long id;

    public CommandToDbRemoveUser(Address from, long userId) {
        super(from);
        id = userId;
    }

    @Override
    protected Consumer<Addressee> changeUserData(DbService db) {
        db.removeUser(id);
        return null;
    }
}
