package ru.otus.java_2017_04.golovnin.hw15.DbService;


import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Message;

import java.util.function.Consumer;

public abstract class AbstractMessageToDb extends Message {

    public AbstractMessageToDb(Address from) {
        super(from);
    }

    @Override
    public Consumer<Addressee> execute(Object obj) {
        if(obj instanceof DbService){
            return execute((DbService)obj);
        }
        else return null;
    }

    public abstract Consumer<Addressee> execute(DbService dbService);
}
