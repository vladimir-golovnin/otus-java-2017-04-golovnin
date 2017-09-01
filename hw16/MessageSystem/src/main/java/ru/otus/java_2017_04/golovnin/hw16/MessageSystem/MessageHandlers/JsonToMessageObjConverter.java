package ru.otus.java_2017_04.golovnin.hw16.MessageSystem.MessageHandlers;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.otus.java_2017_04.golovnin.hw16.MessageSystem.Messages.Message;

public class JsonToMessageObjConverter {
    private static final Gson gson = new Gson();
    public static <T extends Message> T convert(String msg, Class<T> tClass){
        return gson.fromJson(msg, tClass);
    }
}
