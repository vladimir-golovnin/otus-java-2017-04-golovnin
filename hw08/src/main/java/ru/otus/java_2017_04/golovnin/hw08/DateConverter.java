package ru.otus.java_2017_04.golovnin.hw08;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter extends ToJsonConverter{
    @Override
    public String convert(Object obj) {
        String result;

        if(obj.getClass() == Date.class){
            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append("\"");
            resultBuilder.append(DateFormat.getDateTimeInstance(DateFormat.DEFAULT,DateFormat.DEFAULT, Locale.ENGLISH).format((Date)obj));
            resultBuilder.append("\"");
            result = resultBuilder.toString();
        }
        else result = super.convert(obj);
        return result;
    }
}
