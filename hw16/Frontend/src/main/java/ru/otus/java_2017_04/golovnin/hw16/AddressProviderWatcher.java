package ru.otus.java_2017_04.golovnin.hw16;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressProvider;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressesProvideMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AllocateAddressesMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ClientChannel;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Message;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageClient;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandler;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AddressProviderWatcher implements MessageHandler {
    private final AddressProvider addressProvider;
    private final MessageClient messageClient;
    private final Gson gson = new Gson();
    private static final int ADDRESS_COUNT_THRESHOLD = 2;
    private static final int CHECK_PERIOD = 1000; //millis
    private static final int ADDRESS_COUNT_INCREMENT = 5;
    private ScheduledExecutorService executorService;

    public AddressProviderWatcher(AddressProvider addressProvider, MessageClient messageClient) {
        this.addressProvider = addressProvider;
        this.messageClient = messageClient;
    }

    public void start(){
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::watch, 0, CHECK_PERIOD, TimeUnit.MILLISECONDS);
    }

    private void watch(){
        if(addressProvider.getAvailableAddressesCount() < ADDRESS_COUNT_THRESHOLD){
            Message message = new AllocateAddressesMessage(ADDRESS_COUNT_INCREMENT);
            String jsonMessage = gson.toJson(message);
            messageClient.sendMessage(jsonMessage);
        }
    }

    @Override
    public void processMessage(JsonObject jsonObject, ClientChannel channel) {
        if(jsonObject != null && channel != null){
            AddressesProvideMessage message = gson.fromJson(jsonObject, AddressesProvideMessage.class);
            List<Address> providedAddresses = message.getAddresses();
            providedAddresses.forEach(addressProvider::putAddress);
        }
    }
}
