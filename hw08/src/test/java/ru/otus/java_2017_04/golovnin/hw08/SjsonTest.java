package ru.otus.java_2017_04.golovnin.hw08;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * Created by User on 28.05.2017.
 */
public class SjsonTest {
    @Test
    public void simpleJsonTest(){
        JSONArray ar = new JSONArray();
        JSONObject obj = new JSONObject();
        JSONObject resultJson = new JSONObject();

        ar.add("first");
        ar.add(new Integer(100));

        obj.put("one", "two");
        obj.put("three", "four");

        resultJson.put("paramsArray", ar);
        resultJson.put("paramsObj", obj);
        resultJson.put("paramsStr", "some string");
        System.out.print(resultJson.toString());
    }
}
