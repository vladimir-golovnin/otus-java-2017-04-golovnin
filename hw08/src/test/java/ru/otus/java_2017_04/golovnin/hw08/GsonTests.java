package ru.otus.java_2017_04.golovnin.hw08;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

public class GsonTests {

    @Test
    public void primitivesTest(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Handbag bag = new Handbag();
        System.out.println(gson.toJson(bag));
    }

}
