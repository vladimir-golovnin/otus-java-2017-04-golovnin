package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Address;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.ClientChannel;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.CommandProcessor;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.DirectMessage;

public class DirectMessageLocalHandler implements MessageHandler{
    private static final JsonParser parser = new JsonParser();
    private final CommandProcessor cmdProcessor;

    public DirectMessageLocalHandler(CommandProcessor cmdProcessor) {
        this.cmdProcessor = cmdProcessor;
    }

    @Override
    public void processMessage(String msg, ClientChannel channel) {
        if (msg != null) {
            JsonObject msgJsonObj = (JsonObject) parser.parse(msg);
            String cmd = msgJsonObj.get(DirectMessage.COMMAND_FIELD_NAME).getAsString();
            String commandType = msgJsonObj.get(DirectMessage.COMMAND_NAME_FIELD_NAME).getAsString();
            JsonObject addressJsonObj = msgJsonObj.getAsJsonObject(DirectMessage.ADDRESS_FIELD_NAME);
            int addrId = addressJsonObj.get("id").getAsInt();
            Address toAddress = new Address(addrId);
            cmdProcessor.processCommand(commandType, cmd, toAddress);
        }
    }
}
