package ru.otus.java_2017_04.golovnin.hw08;


public class ReflectionHelper {
    private ReflectionHelper(){}

    public static boolean implementsInterface(Class klass, Class interfase){
        boolean result = false;
        if(klass != null){
            Class[] implementedInterfaces = klass.getInterfaces();
            for (Class iface : implementedInterfaces
                    ) {
                if(iface == interfase) result = true;
                else result = implementsInterface(iface, interfase);
                if(result == true) break;
            }
            if(result == false) result = implementsInterface(klass.getSuperclass(), interfase);
        }
        return result;
    }
}
