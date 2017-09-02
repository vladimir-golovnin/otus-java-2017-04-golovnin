package ru.otus.java_2017_04.golovnin.hw16;

import ru.otus.java_2017_04.golovnin.hw16.Cache.MyCache;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbAddUser;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbRemoveUser;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbUpdateUser;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToFrontendAllUsers;
import ru.otus.java_2017_04.golovnin.hw16.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw16.DbService.MySqlDbService;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressProvider;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.CommandProcessor;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.GateWay;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageClient;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers.AddressProviderWatcher;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers.DirectMessageLocalHandler;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessagePacker;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageProcessor;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageSystem;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.AddressesProvideMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.DirectMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ServiceType;

public class DbServerApp
{
    public static void main( String[] args )
    {
        DbService dbService = new MySqlDbService(new MyCache(9, 60000, 45000));

        AddressProvider addressProvider = new AddressProvider(0);
        MessageSystem messageSystem = new MessageSystem(addressProvider);

        MessageProcessor messageProcessor = new MessageProcessor();
        MessageClient messageClient = new MessageClient(messageProcessor);
        MessagePacker messagePacker = new MessagePacker(messageClient);
        GateWay gateWay = new GateWay(messagePacker);
        messageSystem.setGateWay(gateWay);

        AddressProviderWatcher addressProviderWatcher = new AddressProviderWatcher(addressProvider, messagePacker);
        messageProcessor.setHandler(AddressesProvideMessage.class.getSimpleName(), addressProviderWatcher);

        CommandProcessor cmdProcessor = new CommandProcessor(messageSystem);
        cmdProcessor.addCommand(CommandToDbAddUser.class);
        cmdProcessor.addCommand(CommandToDbRemoveUser.class);
        cmdProcessor.addCommand(CommandToDbUpdateUser.class);
        cmdProcessor.addCommand(CommandToFrontendAllUsers.class);
        DirectMessageLocalHandler directMessageLocalHandler = new DirectMessageLocalHandler(cmdProcessor);
        messageProcessor.setHandler(DirectMessage.class.getSimpleName(), directMessageLocalHandler);

        messageClient.start("localhost", 5050);
        addressProviderWatcher.start();

        new Thread(() -> {
            do{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (messageSystem.registerService("Data base", ServiceType.SINGLE, dbService) == null);
        }).start();
    }
}
