package ru.otus.java_2017_04.golovnin.hw12.TestRigMonitor;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.java_2017_04.golovnin.hw12.TestRig.ITestRig;

import java.util.Map;

public class TestRigMonitor {

    public static final String LOGIN_FORM_PATH = "/";
    public static final String ADMIN_PAGE_PATH = "/admin";
    public static final String LOGIN_HANDLER_PATH = "/login";
    public static final String AUTH_ATTRIBUTE_NAME = "authenticated";
    public static final String RETRY_PARAMETER_NAME = "retry";

    private final ITestRig testRig;
    private final int serverPort;

    public TestRigMonitor(int serverPort, ITestRig testRig){
        this.serverPort = serverPort;
        this.testRig = testRig;
    }

    public void start(){

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(FormServlet.class, LOGIN_FORM_PATH);
        context.addServlet(new ServletHolder(new AdminServlet(this)), ADMIN_PAGE_PATH);
        context.addServlet(new ServletHolder(new LoginServlet(new AuthenticationData())), LOGIN_HANDLER_PATH);

        Server server = new Server(serverPort);

        server.setHandler(new HandlerList(context));
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getParameters(){
        return testRig.getParameters();
    }
}
