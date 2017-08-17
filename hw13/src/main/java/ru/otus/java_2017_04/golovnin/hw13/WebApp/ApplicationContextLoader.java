package ru.otus.java_2017_04.golovnin.hw13.WebApp;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextLoader implements ServletContextListener{

    private static final String LOGIN_FORM_PATH = "/login";
    private static final String ERROR_LOGIN_PATH = "/login?retry=true";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("loginFormPath", LOGIN_FORM_PATH);
        servletContext.setAttribute("errorFormPath", ERROR_LOGIN_PATH);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
