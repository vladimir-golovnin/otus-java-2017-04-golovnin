package ru.otus.java_2017_04.golovnin.hw03;


import org.junit.*;

import java.lang.reflect.Field;

public class MyArrayListTests {

    private static int startedTestCounter = 0;
    private static int finishedTestCounter = 0;

    @Before
    public void startedTestsCount()
    {
        startedTestCounter++;
    }

    @After
    public void finishedTestsCount()
    {
        finishedTestCounter++;
    }

    @AfterClass
    public static void showCredits()
    {
        System.out.println(startedTestCounter + " tests started");
        System.out.println(finishedTestCounter + " tests finished");
    }

    @Test
    public void testDefaultConstructor()
    {
        MyArrayList<Object> rat = new MyArrayList<>();
        final int DEFAULT_CAPACITY = 16;
        checkArrayCapacity(rat, DEFAULT_CAPACITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeCapacity()
    {
        MyArrayList<Object> rat = new MyArrayList<>(-1);
    }

    private void checkArrayCapacity(MyArrayList rat, int capacity)
    {
        try {
            Field arrayField = rat.getClass().getDeclaredField("array");
            arrayField.setAccessible(true);
            Object[] actualArray = (Object[]) arrayField.get(rat);
            Object[] expectedArray = new Object[capacity];
            Assert.assertArrayEquals(expectedArray, actualArray);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final int[] CAPACITIES = {0, 1, 2, 10, 23, 86, 256, 1111, 2_205_346};
    @Test
    public void testConstructorWithCapacity()
    {
        for (int capacity: CAPACITIES
             ) {
            MyArrayList<Object> rat = new MyArrayList<>(capacity);
            checkArrayCapacity(rat, capacity);
        }

    }

    @Test
    public void testIsEmpty()
    {
        MyArrayList<Object> rat = new MyArrayList<>();
        Assert.assertTrue(rat.isEmpty());
        rat.add(new Object());
        Assert.assertFalse(rat.isEmpty());
        rat.remove(0);
        Assert.assertTrue(rat.isEmpty());
        rat.add(new Object());
        rat.add(new Object());
        Assert.assertFalse(rat.isEmpty());
        rat.remove(0);
        Assert.assertFalse(rat.isEmpty());
        rat.remove(0);
        Assert.assertTrue(rat.isEmpty());

    }

    private static final int[] TEST_SIZES = {0, 1, 2, 10, 40, 1000};
    @Test
    public void testSize()
    {
        MyArrayList<Object> rat = new MyArrayList<>();
        for (int size : TEST_SIZES
             ) {
            for(int counter = 0; counter < size; counter++){
                rat.add(new Object());
            }
            Assert.assertEquals(size, rat.size());
            rat.clear();
            Assert.assertEquals(0, rat.size());
        }
    }
}
