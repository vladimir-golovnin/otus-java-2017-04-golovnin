package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.AddressProvider;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ClientChannel;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessagePacker;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.AddressesProvideMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.AllocateAddressesMessage;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.Message;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AddressProviderWatcher implements MessageHandler {
    private final AddressProvider addressProvider;
    private final MessagePacker messagePacker;
    private final Gson gson = new Gson();
    private static final int ADDRESS_COUNT_THRESHOLD = 2;
    private static final int CHECK_PERIOD = 1000; //millis
    private static final int ADDRESS_COUNT_INCREMENT = 5;
    private ScheduledExecutorService executorService;

    public AddressProviderWatcher(AddressProvider addressProvider, MessagePacker messagePacker) {
        this.addressProvider = addressProvider;
        this.messagePacker = messagePacker;
    }

    public void start(){
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::watch, 0, CHECK_PERIOD, TimeUnit.MILLISECONDS);
    }

    private void watch(){
        if(addressProvider.getAvailableAddressesCount() < ADDRESS_COUNT_THRESHOLD){
            Message message = new AllocateAddressesMessage(ADDRESS_COUNT_INCREMENT);
            messagePacker.sendMessage(message);
        }
    }

    @Override
    public void processMessage(String msg, ClientChannel channel) {
        if(msg != null){
            AddressesProvideMessage message = gson.fromJson(msg, AddressesProvideMessage.class);
            List<Address> providedAddresses = message.getAddresses();
            providedAddresses.forEach(addressProvider::putAddress);
        }
    }
}
