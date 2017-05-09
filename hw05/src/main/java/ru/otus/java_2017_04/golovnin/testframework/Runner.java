package ru.otus.java_2017_04.golovnin.testframework;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Runner {
    public static void run(Class<?>... testClasses)
    {
        int invokedTestsCounter = 0;
        int failedTestsCounter = 0;
        int errorTestCounter = 0;

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
                        System.out.println("\nRunning tests from " + testClass.getName());
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
                                        System.out.println(" : FAIL");
                                        cause.printStackTrace(System.out);
                                        resultIsOk = false;
                                        failedTestsCounter++;
                                    }else if(cause.getClass() != getExpectedException(test)) {
                                        System.out.println(" : FAIL");
                                        e.printStackTrace(System.out);
                                        resultIsOk = false;
                                        errorTestCounter++;
                                    }
                                }
                                if (resultIsOk) {
                                    System.out.println(" : OK");
                                }
                                invokeMethods(afterTestsMethods, testClassInstance);
                            }
                            else errorTestCounter++;
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        printReport(invokedTestsCounter, failedTestsCounter, errorTestCounter);
    }

    private static void printReport(int total, int failed, int errors){
        System.out.println();
        System.out.print("Tests run: ");
        System.out.print(total);
        System.out.print(", Failures: ");
        System.out.print(failed);
        System.out.print(", Errors: ");
        System.out.print(errors);
        System.out.println();
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

    public static void run(String pack){
        Reflections reflections = new Reflections(pack, new SubTypesScanner(false));
        Set<Class<? extends Object>> classSet = reflections.getSubTypesOf(Object.class);
        Class<?>[] sample = new Class[1];
        run(classSet.toArray(sample));
    }
}
