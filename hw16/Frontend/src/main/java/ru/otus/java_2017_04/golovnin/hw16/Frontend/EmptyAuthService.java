package ru.otus.java_2017_04.golovnin.hw16.Frontend;


import javax.servlet.http.HttpSession;
import java.util.Properties;

public class EmptyAuthService implements AuthenticationService{
    public EmptyAuthService(Properties props){

    }

    @Override
    public void login(HttpSession session, String login, String pass) {

    }

    @Override
    public boolean isAuthorized(HttpSession session) {
        return true;
    }

    @Override
    public int getLoginTriesCount(HttpSession session) {
        return 0;
    }
}
