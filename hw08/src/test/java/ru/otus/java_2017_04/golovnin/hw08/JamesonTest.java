package ru.otus.java_2017_04.golovnin.hw08;


import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class JamesonTest {
    private Gson gson = new Gson();
    private Jameson jameson = new Jameson();

    @Test
    public void testBoolean(){
        boolean testValue = false;
        compareResults(testValue);
    }

    @Test
    public void testChar(){
        char testValue = '4';
        compareResults(testValue);
    }

    @Test
    public void testFloat(){
        float testValue = 3.14f;
        compareResults(testValue);
    }

    @Test
    public void testDouble(){
        double testValue = 2.7182818;
        compareResults(testValue);
    }

    @Test
    public void testShort(){
        short testValue = Short.MAX_VALUE;
        compareResults(testValue);
    }

    @Test
    public void testInt(){
        int testNum = 19121986;
        compareResults(testNum);
    }

    @Test
    public void testLong(){
        long testValue = Long.MAX_VALUE/2;
        compareResults(testValue);
    }

    @Test
    public void testString(){
        String testString = "test";
        compareResults(testString);
    }

    @Test
    public void testStringArray(){
        String[] array = {"two", "Three", "Four", "%@%*()"};
        compareResults(array);
    }

    @Test
    public void testIntArray(){
        int[] array = {1, 2, 3, 99, 1116};
        compareResults(array);
    }

    @Test
    public void testCharArray(){
        char[] chars = {'#', '6', '{', '*'};
        compareResults(chars);
    }

    @Test
    public void testBooleanArray(){
        boolean[] array = {false, true, true, false};
        compareResults(array);
    }

    @Test
    public void testDoubleArray(){
        double[] array = {2.56, 4.16, 3.89, 4930.3849};
        compareResults(array);
    }

    @Test
    public void testDate(){
        Date testDate = new Date();
        compareResults(testDate);
    }

    @Test
    public void testObject(){
        Object testObject = new Handbag();
        compareResults(testObject);
    }

    @Test
    public void testObjectArray(){
        Object[] testArray = {new Handbag(), new Date(), new Fish("Flatfish",10)};
        compareResults(testArray);
    }

    @Test
    public void testList(){
        List<Integer> testList = Arrays.asList(10, 33, 850, 10948);
        compareResults(testList);
    }

    @Test
    public void testMap(){
        Map<String, Integer> testMap = new TreeMap<>();
        testMap.put("Алексей", 35);
        testMap.put("Дмитрий", 40);
        testMap.put("Игорь", 49);
        testMap.put("Владимир", 30);
        testMap.put("Сергей", 60);
        compareResults(testMap);
    }

    @Test
    public void testAnotherMap(){
        Map<Integer, Integer> testMap = new HashMap<>();
        testMap.put(1, 20);
        testMap.put(2, 345);
        testMap.put(3, 1093);
        compareResults(testMap);
    }

    private void compareResults(Object testObject){
        String gsonResult = gson.toJson(testObject);
        System.out.println("Gson result:");
        System.out.println(gsonResult);
        String jamesonResult = jameson.toJson(testObject);
        System.out.println("Jameson result:");
        System.out.println(jamesonResult);
        System.out.println();
        Assert.assertTrue(gsonResult.equals(jamesonResult));
    }
}
