package ru.otus.java_2017_04.golovnin.hw16.Frontend;

import java.util.List;

public interface ClientsNotificator {
    void sendUsersData(List<UserData> users);
}
