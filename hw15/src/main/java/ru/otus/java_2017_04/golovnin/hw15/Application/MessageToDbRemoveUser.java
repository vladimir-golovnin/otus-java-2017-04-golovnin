package ru.otus.java_2017_04.golovnin.hw15.Application;

import ru.otus.java_2017_04.golovnin.hw15.DbService.AbstractMessageToDb;
import ru.otus.java_2017_04.golovnin.hw15.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;

import java.util.function.Consumer;

public class MessageToDbRemoveUser extends AbstractMessageToDb {
    private long id;

    public MessageToDbRemoveUser(Address from, long userId) {
        super(from);
        id = userId;
    }

    @Override
    public Consumer<Addressee> execute(DbService dbService) {
        dbService.removeUser(id);
        return null;
    }
}
