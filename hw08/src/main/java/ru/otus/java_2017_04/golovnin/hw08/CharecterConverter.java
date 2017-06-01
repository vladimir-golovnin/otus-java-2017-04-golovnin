package ru.otus.java_2017_04.golovnin.hw08;


public class CharecterConverter extends ToJsonConverter{
    @Override
    public String convert(Object obj) {
        String result = null;
        if(isCharacter(obj)) {
            StringBuilder builder = new StringBuilder(3);
            builder.append("\"");
            builder.append(obj.toString());
            builder.append("\"");
            result = builder.toString();
        }
        else result = super.convert(obj);
        return result;
    }

    private boolean isCharacter(Object obj){
        return obj.getClass() == Character.class;
    }
}
