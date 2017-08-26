package ru.otus.java_2017_04.golovnin.hw15.DbService;


import java.util.List;

public interface DbService {
    void saveUser(UserDataSet user);
    UserDataSet loadUser(long id);
    List<UserDataSet> getAllUsers();
    void removeUser(long id);
    void shutdown();
    int getAccessCount();
}
