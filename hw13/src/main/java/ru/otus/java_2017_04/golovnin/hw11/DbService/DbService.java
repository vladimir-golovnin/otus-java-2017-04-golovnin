package ru.otus.java_2017_04.golovnin.hw11.DbService;


public interface DbService {
    void saveUser(UserDataSet user);
    UserDataSet loadUser(long id);
    void shutdown();
    int getAccessCount();
}
