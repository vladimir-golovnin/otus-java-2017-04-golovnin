package ru.otus.java_2017_04.golovnin.hw13.WebApp;

import javax.servlet.http.HttpSession;

public interface IAuthenticationService {
    void login(HttpSession session, String login, String pass);
    boolean isAuthorized(HttpSession session);
}
