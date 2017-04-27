package ru.otus.java_2017_04.golovnin.hw04;


import com.sun.management.GarbageCollectorMXBean;

import java.lang.management.ManagementFactory;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        new DrippingBucket().fill();

        List<GarbageCollectorMXBean> gcMBeansList = ManagementFactory.getPlatformMXBeans(GarbageCollectorMXBean.class);
        for(GarbageCollectorMXBean gcMBean : gcMBeansList){
            System.out.println(gcMBean.getName() + ":");
            long collectionDuration = gcMBean.getCollectionTime();
            System.out.println("Collection duration: " + collectionDuration + " ms");
            long collectionCount = gcMBean.getCollectionCount();
            System.out.println("Collections count: " + collectionCount + "\n");
        }
    }

    
}
