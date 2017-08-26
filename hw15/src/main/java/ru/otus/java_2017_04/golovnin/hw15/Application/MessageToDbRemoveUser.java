package ru.otus.java_2017_04.golovnin.hw15.Application;

import ru.otus.java_2017_04.golovnin.hw15.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;
import java.util.function.Consumer;

public class MessageToDbRemoveUser extends MessageToDbUpdateUser {
    private long id;

    public MessageToDbRemoveUser(Address from, long userId) {
        super(from);
        id = userId;
    }

    @Override
    protected Consumer<Addressee> changeUserData(DbService db) {
        db.removeUser(id);
        return null;
    }
}
