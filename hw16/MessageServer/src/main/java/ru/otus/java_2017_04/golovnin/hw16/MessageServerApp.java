package ru.otus.java_2017_04.golovnin.hw16;


import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressProvider;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AllocateAddressesMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AllocateAddressesMessageHandler;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageProcessor;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageServer;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.RouteTable;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.RouteTableImpl;

public class MessageServerApp
{
    public static void main( String[] args )
    {
        AddressProvider addressProvider = new AddressProvider(32);
        MessageProcessor messageProcessor = new MessageProcessor();
        messageProcessor.setHandler(AllocateAddressesMessage.class.getSimpleName(),
                new AllocateAddressesMessageHandler(addressProvider));
        RouteTable routeTable = new RouteTableImpl(addressProvider);
        MessageServer server = new MessageServer(messageProcessor, routeTable);

        server.start(5050);
    }
}
