package ru.otus.java_2017_04.golovnin.hw15;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class App
{
//    public static void main( String[] args )
//    {
//
//        AddressProvider addressProvider = new AddressProvider();
//        addressProvider.putAddresses(new Address(1), new Address(2), new Address(3), new Address(4));
//        MessageSystem messageSystem = new MessageSystem(addressProvider);
//        messageSystem.registerService("Data base", new DbService());
//
//        Server server = new Server(8081);
//
//        // add first handler
//        ResourceHandler resource_handler = new ResourceHandler();
//        resource_handler.setResourceBase("public_html");
//
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[] {new SocketHandler(messageSystem), resource_handler});
//
//        server.setHandler(handlers);
//
//        try {
//            server.start();
//            server.join();
//        } catch (Throwable t) {
//            t.printStackTrace(System.err);
//        }
//    }

}
