package ru.otus.java_2017_04.golovnin.hw16.Commands;


import ru.otus.java_2017_04.golovnin.hw16.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Addressee;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Command;

import java.util.function.Consumer;

public abstract class AbstractCommandToDb extends Command {

    public AbstractCommandToDb(Address from) {
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
