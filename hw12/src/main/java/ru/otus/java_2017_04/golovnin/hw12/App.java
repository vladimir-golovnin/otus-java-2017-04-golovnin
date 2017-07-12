package ru.otus.java_2017_04.golovnin.hw12;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class App
{
    private final static int SERVER_PORT = 8080;
    private final static String resourceDir = "public_resources";

    public static void main( String[] args ) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(resourceDir);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(AdminServlet.class, "/admin");

        Server server = new Server(8080);
        server.setHandler(new HandlerList(resourceHandler, context));
        server.start();
        server.join();
    }


}
