package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers;

import com.google.gson.JsonObject;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ClientChannel;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.RegisterServiceMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ServiceRegistry;

public class RegisterServiceMessageHandler implements MessageHandler {
    private final ServiceRegistry serviceRegistry;

    public RegisterServiceMessageHandler(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void processMessage(String msg, ClientChannel channel) {
        if(msg != null){
            RegisterServiceMessage reconstructedMessage =
                    JsonToMessageObjConverter.convert(msg, RegisterServiceMessage.class);
            serviceRegistry.register(
                    reconstructedMessage.getServiceName(),
                    reconstructedMessage.getServiceType(),
                    reconstructedMessage.getAddress()
            );
        }
    }
}
