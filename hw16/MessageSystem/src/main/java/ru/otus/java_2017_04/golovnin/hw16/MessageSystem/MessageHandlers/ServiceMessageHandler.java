package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ClientChannel;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.DirectMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.ServiceMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.RouteTable;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ServiceRecord;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ServiceRegistry;

import java.util.Collection;
import java.util.Set;

public class ServiceMessageHandler implements MessageHandler {
    private static final JsonParser parser = new JsonParser();
    private static final Gson gson = new Gson();
    private final ServiceRegistry serviceRegistry;
    private final RouteTable routeTable;

    public ServiceMessageHandler(ServiceRegistry serviceRegistry, RouteTable routeTable) {
        this.serviceRegistry = serviceRegistry;
        this.routeTable = routeTable;
    }

    @Override
    public void processMessage(String msg, ClientChannel channel) {
        if(msg != null){
            JsonObject messageObj = (JsonObject) parser.parse(msg);
            String toService = messageObj.get(ServiceMessage.TO_SERVICE_FIELD_NAME).getAsString();
            String commndType = messageObj.get(DirectMessage.COMMAND_NAME_FIELD_NAME).getAsString();
            String command = messageObj.get(DirectMessage.COMMAND_FIELD_NAME).getAsString();

            ServiceRecord serviceRecord = serviceRegistry.getRecord(toService);
            if(serviceRecord != null){
                Set<Address> serviceAddresses = serviceRecord.addresses();
                switch (serviceRecord.type()){
                    case SINGLE:
                        Address toAddress = chooseAddress(serviceAddresses);
                        if(toAddress != null) sendMessage(toAddress, commndType, command);
                        break;

                    case BROADCAST:
                        for (Address address : serviceAddresses){
                            sendMessage(address, commndType, command);
                        }
                        break;
                }
            }
        }
    }

    private void sendMessage(Address address, String commandName, String command){
        ClientChannel toChannel = routeTable.route(address);
        if (toChannel != null) {
            toChannel.sendMessage(gson.toJson(new DirectMessage(address, commandName, command)));
        }
    }

    private Address chooseAddress(Collection<Address> addresses){
        Address result = null;
        byte minload = routeTable.MAX_WORKLOAD;
        for (Address address: addresses
             ) {
            if(routeTable.getWorkloadForAddress(address) <= minload) result = address;
        }
        return result;
    }
}
