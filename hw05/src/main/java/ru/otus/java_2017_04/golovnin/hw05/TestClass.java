package ru.otus.java_2017_04.golovnin.hw05;

/**
 * Created by User on 01.05.2017.
 */
public class TestClass {

    @Test
    public void testSum(){
        if(ClassUnderTest.sum(1,2) != 3) {
            throw new AssertionError();
        }
    }


}
