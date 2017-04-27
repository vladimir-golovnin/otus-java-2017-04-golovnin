package ru.otus.java_2017_04.golovnin.hw04;

public class DrippingBucket {

    public void fill() {
        final int ITERATIONS_NUM = 350;
        final int ELEMENTS_NUM = 1_600_000;
        String[] array = new String[ELEMENTS_NUM];
        int i;
        for (i = 0; i < ITERATIONS_NUM; i++) {

            for (int k = 0; k < ELEMENTS_NUM; k++) {
                array[k] = new String(Integer.toString(i));
            }

            array = new String[ELEMENTS_NUM];

            if (i % (ITERATIONS_NUM/10) == 0 && i != 0) {
                System.out.println(((i*100) / ITERATIONS_NUM) + "% done" );
            }
        }
        System.out.println(((i*100) / ITERATIONS_NUM) + "% done" );
    }
}
