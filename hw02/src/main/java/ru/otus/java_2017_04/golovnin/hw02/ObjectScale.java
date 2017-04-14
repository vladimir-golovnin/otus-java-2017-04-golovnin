package ru.otus.java_2017_04.golovnin.hw02;

import java.io.*;

public class ObjectScale {
    private static final int initialScaleArraySize = 10_000_000;
    private static final int iterationsNumber = 6;

    public int weigh(ObjectFactory factory) {
        int scaleArraySize = initialScaleArraySize;
        boolean arraySizeIsNotSet = true;

        int weigh = 0;
        int correctedWeigh = 0;

        do {
            try {
                Object[] scaleArray = new Object[scaleArraySize];
                int[] weighEstimates = new int[iterationsNumber];
                for (int iteration = 0; iteration < iterationsNumber; iteration++) {

                    for (int index = 0; index < scaleArraySize; index++) {
                            scaleArray[index] = factory.getInstance();
                    }

                    long freeMemoryAfterFillUp = Runtime.getRuntime().freeMemory();

                    scaleArray = new Object[scaleArraySize];
                    System.gc();

                    long freeMemoryAfterGC = Runtime.getRuntime().freeMemory();
                    weighEstimates[iteration] = (int) ((freeMemoryAfterGC - freeMemoryAfterFillUp) / scaleArraySize);
                }

                int estimatesSum = 0;
                //Start with second estimate
                for (int i = 1; i < weighEstimates.length; i++) {
                    estimatesSum += weighEstimates[i];
                }

                weigh = estimatesSum / (iterationsNumber - 1);
                correctedWeigh = weigh % 8 == 0 ? weigh : ( (weigh / 8 + 1)*8);
                arraySizeIsNotSet = false;
            }
            catch (OutOfMemoryError e){
                scaleArraySize /= 2;
            }
        }while (arraySizeIsNotSet);

        return correctedWeigh;
    }

    public int weigh2(Object object)
    {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(object);



            return baos.size();
        } catch (IOException e) {
            return 0;
        }
    }

    private Object cloneObject(Object object)
    {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(object);
            ous.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object clonedObject = ois.readObject();
            baos.close();
            bais.close();
            ois.close();
            return clonedObject;
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }

    }
}
