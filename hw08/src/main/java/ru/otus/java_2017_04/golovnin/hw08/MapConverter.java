package ru.otus.java_2017_04.golovnin.hw08;


import java.util.Map;
import java.util.Set;

public class MapConverter extends ToJsonConverter{
    private ToJsonConverter elementConverter;

    public MapConverter(ToJsonConverter elementsConverter){
        this.elementConverter = elementsConverter;
    }

    @Override
    public String convert(Object obj) {
        String result;
        if (obj != null){
            if(ReflectionHelper.implementsInterface(obj.getClass(), Map.class)){
                StringBuilder resultBuilder = new StringBuilder();
                resultBuilder.append("{");
                Map convertedMap = (Map)obj;
                Set<Object> keySet = convertedMap.keySet();
                for (Object key : keySet
                        ) {
                    resultBuilder.append("\"");
                    resultBuilder.append(key);
                    resultBuilder.append("\":");
                    resultBuilder.append(elementConverter.convert(convertedMap.get(key)));
                    resultBuilder.append(",");
                }
                resultBuilder.replace(resultBuilder.length()-1, resultBuilder.length(), "}");
                result = resultBuilder.toString();
            }
            else result = super.convert(obj);
        }
        else result = super.convert(obj);
        return result;
    }
}
