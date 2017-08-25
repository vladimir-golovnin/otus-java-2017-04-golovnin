package ru.otus.java_2017_04.golovnin.hw15.DbService;


import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.Addressee;

import java.util.function.Consumer;

public class MessageToDbGetUser extends AbstractMessageToDb {
    private final long userId;

    public MessageToDbGetUser(Address from, long userId) {
        super(from);
        this.userId = userId;
    }

    @Override
    public Consumer<Addressee> execute(DbService dbService) {
//        User user= dbService.getUser(userId);
//
//        return (addressee) -> {
//            FrontendUserData frontendUserData = null;
//            if(user != null) frontendUserData = new FrontendUserData(user.getId(), user.getName());
//            addressee.getMessageSystem().
//                    sendMessage(new MessageToFrontendGetUserAnswer(addressee.getAddress(),
//                                    frontendUserData),
//                            getFromAddress());
//        };

        return addressee -> {};
    }
}
