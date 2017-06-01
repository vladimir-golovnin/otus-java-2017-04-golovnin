package ru.otus.java_2017_04.golovnin.hw08;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayConverter extends ToJsonConverter {
    private ToJsonConverter elementsConverter;
    private enum PRIMITIVE_TYPE {BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOLEAN, CHAR};

    private static Map<Class, PRIMITIVE_TYPE> primitiveTypesMap;

    public ArrayConverter(ToJsonConverter elementsConverter){
        this.elementsConverter = elementsConverter;
        primitiveTypesMap = new HashMap<>(PRIMITIVE_TYPE.values().length);
        primitiveTypesMap.put(byte.class, PRIMITIVE_TYPE.BYTE);
        primitiveTypesMap.put(short.class, PRIMITIVE_TYPE.SHORT);
        primitiveTypesMap.put(int.class, PRIMITIVE_TYPE.INT);
        primitiveTypesMap.put(long.class, PRIMITIVE_TYPE.LONG);
        primitiveTypesMap.put(float.class, PRIMITIVE_TYPE.FLOAT);
        primitiveTypesMap.put(double.class, PRIMITIVE_TYPE.DOUBLE);
        primitiveTypesMap.put(boolean.class, PRIMITIVE_TYPE.BOOLEAN);
        primitiveTypesMap.put(char.class, PRIMITIVE_TYPE.CHAR);
    }

    @Override
    public String convert(Object obj) {
        String result;
        if(obj == null) result = super.convert(obj);
        else {
            if (obj.getClass().isArray()) {
                int arrayLength = Array.getLength(obj);
                StringBuilder resultBuilder = new StringBuilder(arrayLength*2);
                resultBuilder.append("[");
                Class componentType = obj.getClass().getComponentType();
                if(componentType.isPrimitive()) {
                    switch (primitiveTypesMap.get(componentType)){
                        case INT:
                            resultBuilder.append(convertElements((int[])obj));
                            break;
                        case BYTE:
                            resultBuilder.append(convertElements((byte[])obj));
                            break;
                        case CHAR:
                            resultBuilder.append(convertElements((char[])obj));
                            break;
                        case LONG:
                            resultBuilder.append(convertElements((char[])obj));
                            break;
                        case FLOAT:
                            resultBuilder.append(convertElements((float[])obj));
                            break;
                        case SHORT:
                            resultBuilder.append(convertElements((short[])obj));
                            break;
                        case DOUBLE:
                            resultBuilder.append(convertElements((double[])obj));
                            break;
                        case BOOLEAN:
                            resultBuilder.append(convertElements((boolean[])obj));
                    }
                }
                else {
                    Object[] array = (Object[])obj;
                    resultBuilder.append(convertElements(array));
                }
                resultBuilder.replace(resultBuilder.length()-1, resultBuilder.length(), "]");
                result = resultBuilder.toString();
            }
            else result = super.convert(obj);
        }
        return result;
    }

    private String convertElements(byte[] array) {
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (byte element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private String convertElements(short[] array) {
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (short element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private String convertElements(int[] array) {
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (int element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private String convertElements(long[] array) {
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (long element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private String convertElements(float[] array) {
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (float element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private String convertElements(double[] array) {
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (double element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private String convertElements(boolean[] array) {
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (boolean element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private String convertElements(char[] array) {
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (char element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

    private String convertElements(Object[] array){
        StringBuilder resultBuilder = new StringBuilder(array.length*2);
        for (Object element:array
                ) {
            resultBuilder.append(elementsConverter.convert(element));
            resultBuilder.append(",");
        }
        return resultBuilder.toString();
    }

}
