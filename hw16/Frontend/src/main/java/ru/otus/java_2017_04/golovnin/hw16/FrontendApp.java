package ru.otus.java_2017_04.golovnin.hw16;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.AdminServlet;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.FormServlet;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.LoginServlet;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.SocketHandler;


public class FrontendApp
{
    private static final String LOGIN_FORM_PATH = "/login";
    private static final String ADMIN_SERVLET_PATH = "/";
    private static final String LOGIN_SERVLET_PATH = "/auth";
    private static final String SOCKET_SERVLET_PATH = "/socket";
    private static final String APP_CONTEXT_FILE = "applicationContext.xml";
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";
    private static final String AUTH_SERVICE_BEAN_NAME = "AuthenticationService";
    private static final String AUTH_SERVICE_ATTRIBUTE_NAME = "authService";

    public static void main( String[] args )
    {
        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.addServlet(FormServlet.class, LOGIN_FORM_PATH);
        servletContext.addServlet(AdminServlet.class, ADMIN_SERVLET_PATH);
        servletContext.addServlet(LoginServlet.class, LOGIN_SERVLET_PATH);
        servletContext.addServlet(SocketHandler.class, SOCKET_SERVLET_PATH);

        servletContext.setAttribute("loginFormPath", LOGIN_FORM_PATH);
        ApplicationContext appContext = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE);
        servletContext.setAttribute(APP_CONTEXT_ATTRIBUTE_NAME, appContext);
        servletContext.setAttribute(AUTH_SERVICE_ATTRIBUTE_NAME, appContext.getBean(AUTH_SERVICE_BEAN_NAME));

        int serverPort = Integer.parseUnsignedInt(args[0]);
        Server server = new Server(serverPort);
        server.setHandler(servletContext);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
