package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ClientChannel;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.AddressesProvideMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.AllocateAddressesMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.Message;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.RouteTable;


public class AllocateAddressesMessageHandler implements MessageHandler {
    private static final JsonParser parser = new JsonParser();
    private static final Gson gson = new Gson();

    private final RouteTable routeTable;

    public AllocateAddressesMessageHandler(RouteTable routeTable){
        this.routeTable = routeTable;
    }

    @Override
    public void processMessage(String msg, ClientChannel channel) {
        if(msg != null && channel != null){
            JsonObject jsonObject = (JsonObject) parser.parse(msg);
            String messageType = jsonObject.get(Message.TYPE_FIELD).getAsString();
            if(messageType.equals(AllocateAddressesMessage.class.getSimpleName())){

                int addressesNum = jsonObject.get(AllocateAddressesMessage.ADDRESSES_NUM_FIELD_NAME).getAsInt();
                AddressesProvideMessage responseMessage = new AddressesProvideMessage();
                for ( ;addressesNum > 0; addressesNum--){
                    Address address = routeTable.getAddress(channel);
                    if (address != null) {
                        responseMessage.addAddress(address);
                    }
                    else break;
                }
                channel.sendMessage(gson.toJson(responseMessage));
            }
        }
    }
}
