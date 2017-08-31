package ru.otus.java_2017_04.golovnin.hw16.Frontend;

import javax.servlet.http.HttpSession;
import java.util.Properties;


public class StupidAuthenticationService implements AuthenticationService {
    private static final String AUTH_ATTRIBUTE_NAME = "authorized";
    private static final String LOGIN_ATTRIBUTE_NAME = "login";
    private static final String LOGIN_TRIES_COUNT_ATTRIBUTE_NAME = "loginTriesCount";
    private final Properties keys;

    public StupidAuthenticationService(Properties keys){
        this.keys = keys;
    }

    @Override
    public void login(HttpSession session, String login, String pass) {
        incrementLoginTriesCount(session);
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

    @Override
    public int getLoginTriesCount(HttpSession session) {
        Integer triesCount = (Integer)session.getAttribute(LOGIN_TRIES_COUNT_ATTRIBUTE_NAME);
        return triesCount == null ? 0 : triesCount;
    }

    private void incrementLoginTriesCount(HttpSession session){
        Integer triesCount = (Integer)session.getAttribute(LOGIN_TRIES_COUNT_ATTRIBUTE_NAME);
        if(triesCount == null) triesCount = 1;
        else triesCount++;
        session.setAttribute(LOGIN_TRIES_COUNT_ATTRIBUTE_NAME, triesCount);
    }
}
