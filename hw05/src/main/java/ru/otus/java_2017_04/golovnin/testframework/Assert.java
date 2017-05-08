package ru.otus.java_2017_04.golovnin.testframework;


public class Assert {
    public static void fail(){
        throw new AssertionError();
    }

    public static void assertTrue(boolean condition){
        if(!condition) throw new AssertionError();
    }

    public static void assertFalse(boolean condition){
        if(condition) throw new AssertionError();
    }

    public static void assertNotNull(Object o) {
        if(o == null) throw new AssertionError();
    }

    public static void assertEquals(int expected, int actuals){
        if(expected != actuals) throw new AssertionError();
    }


}
