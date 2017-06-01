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
        int[] array = {5, 6, 7, 8};

        System.out.println(gson.toJson(array));
        System.out.println(gson.toJson("hello"));
        System.out.println(gson.toJson(4));
        System.out.println(gson.toJson(new Thread()));
    }

}
