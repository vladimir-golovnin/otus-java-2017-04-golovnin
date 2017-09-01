package ru.otus.java_2017_04.golovnin.hw16;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.java_2017_04.golovnin.hw16.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.AdminServlet;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.ClientsNotificator;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.FormServlet;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.LoginServlet;
import ru.otus.java_2017_04.golovnin.hw16.Frontend.SocketHandler;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageSystem;

public class FrontendServer {

    private static final String LOGIN_FORM_PATH = "/login";
    private static final String ADMIN_SERVLET_PATH = "/";
    private static final String LOGIN_SERVLET_PATH = "/auth";
    private static final String SOCKET_SERVLET_PATH = "/socket";
    private static final String APP_CONTEXT_FILE = "applicationContext.xml";
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";
    private static final String AUTH_SERVICE_BEAN_NAME = "AuthenticationService";
    private static final String AUTH_SERVICE_ATTRIBUTE_NAME = "authService";
    private static final String NOTIFICATION_SERVICE_NAME = "Clients notificator";


    ApplicationContext appContext;

    private ServletContextHandler servletContext;

    public FrontendServer(ApplicationContext appContext) {
        this.appContext = appContext;
        servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.addServlet(FormServlet.class, LOGIN_FORM_PATH);
        servletContext.addServlet(AdminServlet.class, ADMIN_SERVLET_PATH);
        servletContext.addServlet(LoginServlet.class, LOGIN_SERVLET_PATH);
        servletContext.addServlet(SocketHandler.class, SOCKET_SERVLET_PATH);
        servletContext.setAttribute("loginFormPath", LOGIN_FORM_PATH);
        servletContext.setAttribute(APP_CONTEXT_ATTRIBUTE_NAME, appContext);
        servletContext.setAttribute(AUTH_SERVICE_ATTRIBUTE_NAME, appContext.getBean(AUTH_SERVICE_BEAN_NAME));


    }

    public void start(int port){
        Server server = new Server(port);
        server.setHandler(servletContext);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(server.isRunning()){
            MessageSystem messageSystem = (MessageSystem) appContext.getBean("MessageSystem");
            ClientsNotificator notificator = (ClientsNotificator)appContext.getBean("SocketCreator");
            DbService dbService = (DbService)appContext.getBean("DbService");
            new Thread(() -> {
                 do{
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                } while (messageSystem.registerService(NOTIFICATION_SERVICE_NAME, notificator) == null);
            }).start();

            new Thread(() -> {
                do{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (messageSystem.registerService("Data base", dbService) == null);
            }).start();
        }
    }
}
