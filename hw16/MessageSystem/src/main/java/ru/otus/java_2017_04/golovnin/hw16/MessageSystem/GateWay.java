package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import com.google.gson.Gson;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.DirectMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.RegisterServiceMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.ServiceMessage;

public class GateWay {
    private final MessagePacker packer;
    private final Gson gson = new Gson();

    public GateWay(MessagePacker packer){
        this.packer = packer;
    }

    public void send(Command cmd, Address address){
        DirectMessage message = new DirectMessage(address, cmd.getClass().getSimpleName(), gson.toJson(cmd));
        packer.sendMessage(message);
    }

    public void sendToService(Command cmd, String serviceName){
        ServiceMessage message = new ServiceMessage(serviceName, cmd.getClass().getSimpleName(), gson.toJson(cmd));
        packer.sendMessage(message);
    }

    public void registerService(String serviceName, ServiceType type, Address address){
        RegisterServiceMessage message = new RegisterServiceMessage(serviceName, type, address);
        packer.sendMessage(message);
    }
}
