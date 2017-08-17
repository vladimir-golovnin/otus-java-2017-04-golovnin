package ru.otus.java_2017_04.golovnin.hw13.WebApp;

public interface IAuthenticationData {
    boolean checkValid(String login, String pass);
}
