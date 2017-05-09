package ru.otus.java_2017_04.golovnin.testframework;


public class Assert {
    public static void fail(){
        throw new AssertionError();
    }

    public static void assertTrue(boolean condition){
        if(!condition) fail();
    }

    public static void assertFalse(boolean condition){
        if(condition) fail();
    }

    public static void assertNotNull(Object o) {
        if(o == null) fail();
    }

    public static void assertEquals(int expected, int actuals){
        if(expected != actuals) fail();
    }


}
