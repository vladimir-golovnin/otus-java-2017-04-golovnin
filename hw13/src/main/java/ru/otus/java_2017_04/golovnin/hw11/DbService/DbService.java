package ru.otus.java_2017_04.golovnin.hw11.DbService;


public interface DbService {
    void saveUser(UserDataSet user);
    UserDataSet loadUser(long id);
    void shutdown();

    int getCacheHits();

    int getCacheMiss();

    float getCacheFillFactor();

    int getAccessCount();
}
