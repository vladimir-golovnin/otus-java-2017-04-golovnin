package ru.otus.java_2017_04.golovnin.hw05;


import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        Object testObject = new TestClass();

        Method[] testMethods = testObject.getClass().getMethods();
        List<Method> methodsList = Arrays.asList(testMethods);
        methodsList.forEach(m -> {
            if(m.getAnnotation(Test.class) != null) {
                try {
                    m.invoke(testObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
