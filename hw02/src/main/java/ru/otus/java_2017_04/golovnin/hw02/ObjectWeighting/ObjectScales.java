package ru.otus.java_2017_04.golovnin.hw02.ObjectWeighting;


public class ObjectScales {
    private static final int initialScaleArraySize = 10_000_000;
    private static final int iterationsNumber = 6;

    public int weigh(ObjectFactory factory) {
        int scaleArraySize = initialScaleArraySize;
        boolean arraySizeIsNotSet = true;

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

                int weight = estimatesSum / (iterationsNumber - 1);
                correctedWeigh = weight % Byte.SIZE == 0 ? weight : ( (weight / Byte.SIZE + 1)*Byte.SIZE);
                arraySizeIsNotSet = false;
            }
            catch (OutOfMemoryError e){
                scaleArraySize /= 2;
            }
        }while (arraySizeIsNotSet);

        return correctedWeigh;
    }
}
