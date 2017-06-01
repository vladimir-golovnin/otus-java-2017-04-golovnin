package ru.otus.java_2017_04.golovnin.hw08;


public class StringConverter extends ToJsonConverter{
    @Override
    public String convert(Object obj) {
        String result = null;
        if(obj.getClass() == String.class) {
            StringBuilder builder = new StringBuilder(((CharSequence)obj).length() + "\"\"".length());
            builder.append("\"");
            builder.append(obj.toString());
            builder.append("\"");
            result = builder.toString();
        }
        else result = super.convert(obj);
        return result;
    }

}
