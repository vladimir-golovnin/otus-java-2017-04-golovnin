package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ClientChannel;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.DirectMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.RouteTable;

public class DirectMessageHandler implements MessageHandler{
    private static final Gson gson = new Gson();
    private static final JsonParser parser = new JsonParser();
    private final RouteTable routeTable;

    public DirectMessageHandler(RouteTable routeTable) {
        this.routeTable = routeTable;
    }

    @Override
    public void processMessage(String msg, ClientChannel channel) {
        if(msg != null){
            JsonObject jsonObject = (JsonObject) parser.parse(msg);
            JsonObject addressObj = jsonObject.getAsJsonObject(DirectMessage.ADDRESS_FIELD_NAME);
            Address toAddress = gson.fromJson(jsonObject, Address.class);
            ClientChannel toChannel = routeTable.route(toAddress);
            if(toChannel != null) toChannel.sendMessage(msg);
        }
    }
}
