package ru.otus.java_2017_04.golovnin.hw15.Frontend;


import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Message;
import java.util.function.Consumer;

public abstract class AbstractMessageToFrontend extends Message {
    public AbstractMessageToFrontend(Address from) {
        super(from);
    }

    @Override
    public Consumer<Addressee> execute(Object obj) {
        if(obj instanceof MySocket){
            return execute((MySocket)obj);
        }
        return (ms) -> {};
    }

    public abstract Consumer<Addressee> execute(MySocket socket);
}
