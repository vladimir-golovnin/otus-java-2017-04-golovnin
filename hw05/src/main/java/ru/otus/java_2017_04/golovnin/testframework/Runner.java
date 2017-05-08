package ru.otus.java_2017_04.golovnin.testframework;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Runner {
    public static void run(Class<?>... testClasses)
    {
        for (Class<?> testClass : testClasses
             ) {
            throw new NotImplementedException();
        }
    }
}
