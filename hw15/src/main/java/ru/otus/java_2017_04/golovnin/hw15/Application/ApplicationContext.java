package ru.otus.java_2017_04.golovnin.hw15.Application;

import ru.otus.java_2017_04.golovnin.hw15.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw15.Frontend.ClientsNotificator;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.MessageSystem;

public class ApplicationContext {
    private final MessageSystem messageSystem;

    public ApplicationContext(MessageSystem ms){
        messageSystem = ms;
    }

    public void setClientsNotificationService(ClientsNotificator notificator){
        messageSystem.registerService("Clients notificator", notificator);
    }

    public void setDbService(DbService dbService){
        messageSystem.registerService("Data base", dbService);
    }
}
