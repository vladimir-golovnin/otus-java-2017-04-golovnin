package ru.otus.java_2017_04.golovnin.hw08;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectConverter extends ToJsonConverter{
    private ToJsonConverter elementsConverter;

    public ObjectConverter(ToJsonConverter elementsConverter){
        this.elementsConverter = elementsConverter;
    }

    @Override
    public String convert(Object obj) {
        String result;
        if(obj != null){
            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append("{");
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field:fields
                 ) {
                    resultBuilder.append(convertField(field,obj));
                }
            resultBuilder.replace(resultBuilder.length()-1, resultBuilder.length(), "}");
            result = resultBuilder.toString();
        }
        else result = super.convert(obj);
        return result;
    }

    private boolean isViewable(Field field, Object obj){
        boolean result = false;
        if(!Modifier.isTransient(field.getModifiers())){
            try {
                if(field.get(obj) != null) result = true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String convertField(Field field, Object obj){
        StringBuilder resultBuilder = new StringBuilder();
        boolean fieldIsAccessible = field.isAccessible();
        field.setAccessible(true);
        if(isViewable(field, obj)) {
            resultBuilder.append("\"");
            resultBuilder.append(field.getName());
            resultBuilder.append("\":");
            try {
                resultBuilder.append(elementsConverter.convert(field.get(obj)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            resultBuilder.append(",");
        }
        field.setAccessible(fieldIsAccessible);
        return resultBuilder.toString();
    }
}
