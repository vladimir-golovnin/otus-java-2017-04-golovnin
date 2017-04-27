package ru.otus.java_2017_04.golovnin.hw04;

import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

public class GCSpyFactory {
    public static List<GCSpy> getGCSpies(){
        List<GCSpy> spiesList = new ArrayList<>(2);
        List<GarbageCollectorMXBean> gcBeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean gcbean : gcBeans) {
            spiesList.add(new GCSpy(gcbean));
        }
        return spiesList;
    }
}
