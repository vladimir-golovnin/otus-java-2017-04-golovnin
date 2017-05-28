package ru.otus.java_2017_04.golovnin.hw08;


import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class JamesonTest {
    @Test
    public void testJamesonVersusGson(){
        Handbag testObject = new Handbag();
        Gson gson = new Gson();
        Jameson jameson = new Jameson();
        String gsonResult = gson.toJson(testObject);
        System.out.println("Gson result:");
        System.out.println(gsonResult);
        String jamesonResult = jameson.toJson(testObject);
        System.out.println("Jameson result:");
        System.out.println(jamesonResult);
        Assert.assertTrue(gsonResult.equals(jamesonResult));
    }
}
