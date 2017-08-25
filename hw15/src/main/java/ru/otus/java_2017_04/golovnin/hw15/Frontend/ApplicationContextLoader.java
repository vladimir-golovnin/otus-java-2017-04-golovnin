package ru.otus.java_2017_04.golovnin.hw15.Frontend;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextLoader implements ServletContextListener{

    private static final String LOGIN_FORM_PATH = "/login";
    private static final String APP_CONTEXT_FILE = "applicationContext.xml";
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";
    private static final String AUTH_SERVICE_BEAN_NAME = "AuthenticationService";
    private static final String AUTH_SERVICE_ATTRIBUTE_NAME = "authService";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("loginFormPath", LOGIN_FORM_PATH);
        ApplicationContext appContext = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE);
        servletContext.setAttribute(APP_CONTEXT_ATTRIBUTE_NAME, appContext);
        servletContext.setAttribute(AUTH_SERVICE_ATTRIBUTE_NAME, appContext.getBean(AUTH_SERVICE_BEAN_NAME));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
