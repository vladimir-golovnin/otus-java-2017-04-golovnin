package ru.otus.java_2017_04.golovnin.hw16;


import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressProvider;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers.DirectMessageHandler;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers.ServiceMessageHandler;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.AllocateAddressesMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers.AllocateAddressesMessageHandler;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageProcessor;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageServer;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.DirectMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.RegisterServiceMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers.RegisterServiceMessageHandler;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.RouteTable;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.RouteTableImpl;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ServiceRegistry;

public class MessageServerApp
{
    public static void main( String[] args )
    {
        AddressProvider addressProvider = new AddressProvider(32);
        ServiceRegistry serviceRegistry = new ServiceRegistry();
        MessageProcessor messageProcessor = new MessageProcessor();

        messageProcessor.setHandler(AllocateAddressesMessage.class.getSimpleName(),
                new AllocateAddressesMessageHandler(addressProvider));

        messageProcessor.setHandler(RegisterServiceMessage.class.getSimpleName(),
                new RegisterServiceMessageHandler(serviceRegistry));

        RouteTable routeTable = new RouteTableImpl(addressProvider);

        messageProcessor.setHandler(DirectMessage.class.getSimpleName(),
                new DirectMessageHandler(routeTable));

        messageProcessor.setHandler(DirectMessage.class.getSimpleName(),
                new ServiceMessageHandler(serviceRegistry, routeTable));

        MessageServer server = new MessageServer(messageProcessor, routeTable);

        server.start(5050);
    }
}
