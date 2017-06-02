package ru.otus.java_2017_04.golovnin.hw08;


public class IterableConverter extends ToJsonConverter{
    private ToJsonConverter elementsConverter;

    public IterableConverter(ToJsonConverter converter){
        this.elementsConverter = converter;
    }

    @Override
    public String convert(Object obj) {
        String result;
        if (obj != null){
            if(ReflectionHelper.implementsInterface(obj.getClass(), Iterable.class)){
                StringBuilder resultBuilder = new StringBuilder();
                resultBuilder.append("[");
                for (Object element:(Iterable)obj
                     ) {
                    resultBuilder.append(elementsConverter.convert(element));
                    resultBuilder.append(",");
                }
                resultBuilder.replace(resultBuilder.length()-1, resultBuilder.length(), "]");
                result = resultBuilder.toString();
            }
            else result = super.convert(obj);
        }
        else result = super.convert(obj);
        return result;
    }

}
