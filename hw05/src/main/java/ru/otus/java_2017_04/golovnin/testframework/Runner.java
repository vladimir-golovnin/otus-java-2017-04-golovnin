package ru.otus.java_2017_04.golovnin.testframework;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Runner {
    public static void run(Class<?>... testClasses)
    {
        int invokedTestsCounter = 0;
        int passedTestsCounter = 0;

        for (Class<?> testClass : testClasses
             ) {
            boolean hasEmptyConstructor = true;
            try {
                testClass.getConstructor();
            } catch (NoSuchMethodException e) {
                hasEmptyConstructor = false;
            }
            if(hasEmptyConstructor) {
                Method[] methods = testClass.getDeclaredMethods();
                List<Method> beforeTestsMethods = new LinkedList<>();
                Queue<Method> testMethods = new LinkedList<>();
                List<Method> afterTestsMethods = new LinkedList<>();
                for (Method method : methods
                        ) {
                    if (method.getParameterCount() == 0 && !Modifier.isStatic(method.getModifiers())) {
                        if (isAnnotatedTest(method)){
                            testMethods.add(method);
                        }
                        if (isAnnotatedBefore(method)) {
                            beforeTestsMethods.add(method);
                        }
                        if (isAnnotatedAfter(method)) {
                            afterTestsMethods.add(method);
                        }
                    }
                }

                if(!testMethods.isEmpty()) {
                    try {
                        Object testClassInstance = testClass.newInstance();
                        System.out.println("Running tests from " + testClass.getName());
                        Method test;
                        while ((test = testMethods.poll()) != null) {
                            if(invokeMethods(beforeTestsMethods, testClassInstance)) {
                                System.out.print("Running test " + test.getName());
                                invokedTestsCounter++;
                                boolean resultIsOk = true;
                                try {
                                    test.invoke(testClassInstance);
                                } catch (InvocationTargetException e) {
                                    Throwable cause = e.getCause();
                                    if (cause.getClass() == AssertionError.class) {
                                        System.out.println();
                                        System.out.flush();
                                        try {
                                            TimeUnit.MILLISECONDS.sleep(10);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }

                                        cause.printStackTrace();
                                        System.err.flush();
                                        resultIsOk = false;
                                    }else if(cause.getClass() == getExpectedException(test)) {

                                    }
                                    else {
                                        e.printStackTrace();
                                        resultIsOk = false;
                                    }

                                }
                                if (resultIsOk) {
                                    System.out.println(" : OK");
                                    passedTestsCounter++;
                                }
                                invokeMethods(afterTestsMethods, testClassInstance);
                            }

                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        printReport(invokedTestsCounter, passedTestsCounter);
    }

    private static void printReport(int total, int passed){
        System.out.println();
        System.out.println(total + " tests have been run");
        System.out.println(passed + " tests have been passed");
        System.out.println((total - passed) + " tests have been failed");
    }

    private static boolean invokeMethods(List<Method> methods, Object instance){
        boolean invokationOk = true;
        for(Method method : methods){
            try{
                method.invoke(instance);
            }catch (IllegalAccessException | InvocationTargetException e){
                e.printStackTrace();
                invokationOk = false;
                break;
            }
        }
        return invokationOk;
    }

    private static boolean isAnnotatedBefore(Method method){
        return (method.getAnnotation(Before.class) != null);
    }

    private static boolean isAnnotatedAfter(Method method){
        return (method.getAnnotation(After.class) != null);
    }

    private static boolean isAnnotatedTest(Method method){
        return (method.getAnnotation(Test.class) != null);
    }

    private static Class<? extends Throwable> getExpectedException(Method method){
        Class<? extends Throwable> expectedException = null;
        Test testAnnotation = method.getAnnotation(Test.class);
        if(testAnnotation != null){
            expectedException = testAnnotation.expected();
        }
        return expectedException;
    }
}
