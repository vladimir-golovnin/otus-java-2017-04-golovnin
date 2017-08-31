package ru.otus.java_2017_04.golovnin.hw16.Frontend;

import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class SocketHandler extends WebSocketServlet {
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";
    private static final String SOCKET_CREATOR_BEAN_ID = "SocketCreator";

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        ServletContext servletContext = this.getServletContext();
        ApplicationContext appContext = (ApplicationContext) servletContext.getAttribute(APP_CONTEXT_ATTRIBUTE_NAME);
        WebSocketCreator creator = (WebSocketCreator) appContext.getBean(SOCKET_CREATOR_BEAN_ID);
        webSocketServletFactory.setCreator(creator);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
