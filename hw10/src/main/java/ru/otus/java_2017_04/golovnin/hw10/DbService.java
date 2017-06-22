package ru.otus.java_2017_04.golovnin.hw10;


public interface DbService {
    void saveUser(UserDataSet user);
    UserDataSet loadUser(long id);
}
