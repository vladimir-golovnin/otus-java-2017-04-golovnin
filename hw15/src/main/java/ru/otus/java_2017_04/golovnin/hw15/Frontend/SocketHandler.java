package ru.otus.java_2017_04.golovnin.hw15.Frontend;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.MessageSystem;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class SocketHandler extends WebSocketServlet {
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";
    private static final String MESSAGE_SYSTEM_BEAN_ID = "MessageSystem";

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        ServletContext servletContext = this.getServletContext();
        ApplicationContext appContext = (ApplicationContext) servletContext.getAttribute(APP_CONTEXT_ATTRIBUTE_NAME);
        MessageSystem ms = (MessageSystem) appContext.getBean(MESSAGE_SYSTEM_BEAN_ID);
        webSocketServletFactory.setCreator(new MySocketCreator(ms));
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
