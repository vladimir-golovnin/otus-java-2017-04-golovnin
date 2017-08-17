package ru.otus.java_2017_04.golovnin.hw13.WebApp;

import javax.servlet.http.HttpSession;
import java.util.Properties;


public class StupidAuthenticationService implements AuthenticationService {
    private static final String AUTH_ATTRIBUTE_NAME = "authorized";
    private static final String LOGIN_ATTRIBUTE_NAME = "login";
    private final Properties keys = new Properties();

    public StupidAuthenticationService(){
        keys.setProperty("Vladimir", "123");
        keys.setProperty("Vitaliy", "tully");
    }

    @Override
    public void login(HttpSession session, String login, String pass) {
        if(keys.containsKey(login) && keys.getProperty(login).equals(pass)){
            session.setAttribute(AUTH_ATTRIBUTE_NAME, true);
            session.setAttribute(LOGIN_ATTRIBUTE_NAME, login);
        }
    }

    @Override
    public boolean isAuthorized(HttpSession session) {
        Boolean authorized = (Boolean)session.getAttribute(AUTH_ATTRIBUTE_NAME);
        return authorized != null && authorized;
    }
}
