package ru.otus.java_2017_04.golovnin.hw16.MessageSystem;


import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class AllocateAddressesMessageHandler implements MessageHandler{
    private final Gson gson = new Gson();
    private final AddressProvider addressProvider;

    public AllocateAddressesMessageHandler(AddressProvider addressProvider){
        this.addressProvider = addressProvider;
    }

    @Override
    public void processMessage(JsonObject jsonObject, ClientChannel channel) {
        if(jsonObject != null && channel != null){
            String messageType = jsonObject.get(Message.TYPE_FIELD).getAsString();
            if(messageType.equals(AllocateAddressesMessage.class.getSimpleName())){

                int addressesNum = jsonObject.get(AllocateAddressesMessage.ADDRESSES_NUM_FIELD_NAME).getAsInt();
                AddressesProvideMessage responseMessage = new AddressesProvideMessage();
                for ( ;addressesNum > 0; addressesNum--){
                    Address address = addressProvider.getAddress();
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
