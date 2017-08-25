package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;

import java.util.function.Consumer;

public class MessageToFrontendGetUserAnswer extends AbstractMessageToFrontend{
    private final FrontendUserData data;

    public MessageToFrontendGetUserAnswer(Address from, FrontendUserData data) {
        super(from);
        this.data = data;
    }

    @Override
    public Consumer<Addressee> execute(MySocket socket) {
        socket.sendUserData(data);
        return (addressee -> {});
    }
}
