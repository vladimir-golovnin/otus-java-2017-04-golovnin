package ru.otus.java_2017_04.golovnin.hw16;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbAddUser;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbRemoveUser;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToDbUpdateUser;
import ru.otus.java_2017_04.golovnin.hw16.Commands.CommandToFrontendAllUsers;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressProvider;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.CommandProcessor;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers.AddressProviderWatcher;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers.DirectMessageLocalHandler;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.AddressesProvideMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.GateWay;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageClient;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessagePacker;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageProcessor;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageSystem;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.DirectMessage;

public class FrontendApp
{
    private static final String APP_CONTEXT_FILE = "applicationContext.xml";
    private static final String ADDRESS_PROVIDER_BEAN_NAME = "AddressProvider";
    private static final String MESSAGE_SYSTEM_BEAN_NAME = "MessageSystem";

    public static void main( String[] args )
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE);
        AddressProvider addressProvider = (AddressProvider) appContext.getBean(ADDRESS_PROVIDER_BEAN_NAME);

        MessageProcessor messageProcessor = new MessageProcessor();
        MessageClient messageClient = new MessageClient(messageProcessor);
        MessagePacker messagePacker = new MessagePacker(messageClient);

        GateWay gateWay = new GateWay(messagePacker);
        MessageSystem ms = (MessageSystem)appContext.getBean(MESSAGE_SYSTEM_BEAN_NAME);
        ms.setGateWay(gateWay);

        AddressProviderWatcher watcher = new AddressProviderWatcher(addressProvider, messagePacker);
        messageProcessor.setHandler(AddressesProvideMessage.class.getSimpleName(), watcher);

        CommandProcessor cmdProcessor = new CommandProcessor(ms);
        cmdProcessor.addCommand(CommandToDbAddUser.class);
        cmdProcessor.addCommand(CommandToDbRemoveUser.class);
        cmdProcessor.addCommand(CommandToDbUpdateUser.class);
        cmdProcessor.addCommand(CommandToFrontendAllUsers.class);
        DirectMessageLocalHandler directMessageLocalHandler = new DirectMessageLocalHandler(cmdProcessor);
        messageProcessor.setHandler(DirectMessage.class.getSimpleName(), directMessageLocalHandler);

        messageClient.start("localhost", 5050);
        watcher.start();

        int frontendServerPort = Integer.parseUnsignedInt(args[0]);
        FrontendServer frontendServer = new FrontendServer(appContext);
        frontendServer.start(frontendServerPort);
        System.out.println("Frontend started on port " + frontendServerPort);
    }


}
