package ru.otus.java_2017_04.golovnin.hw13.WebApp;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextLoader implements ServletContextListener{

    private static final String LOGIN_FORM_PATH = "/login";
    private static final String ERROR_LOGIN_PATH = "/login?retry=true";
    private static final String APP_CONTEXT_FILE = "applicationContext.xml";
    private static final String APP_CONTEXT_ATTRIBUTE_NAME = "appContext";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("loginFormPath", LOGIN_FORM_PATH);
        servletContext.setAttribute("errorFormPath", ERROR_LOGIN_PATH);
        ApplicationContext appContext = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE);
        servletContext.setAttribute(APP_CONTEXT_ATTRIBUTE_NAME, appContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
