package ru.otus.java_2017_04.golovnin.hw04;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;


public class GCSpy implements NotificationListener{

    GarbageCollectorMXBean observingGC;
    private long observationStartTimeMillis = 0;
    private long observationStopTimeMillis = 0;
    private long accumulatedWorkDuration = 0;
    private long maxStopTime = 0;
    private int collectionsCounter = 0;


    public GCSpy(GarbageCollectorMXBean gcMBean){
        observingGC = gcMBean;
    }

    public void startObservation(){
            observationStartTimeMillis = System.currentTimeMillis();
            accumulatedWorkDuration = 0;
            collectionsCounter = 0;
            maxStopTime = 0;
            ((NotificationEmitter) observingGC).addNotificationListener(this, null, null);
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            long workDuration =  info.getGcInfo().getDuration();
            if(workDuration > maxStopTime) maxStopTime = workDuration;
            accumulatedWorkDuration += workDuration;
            collectionsCounter++;
        }
    }

    public void stopObservation(){
            try {
                ((NotificationEmitter) observingGC).removeNotificationListener(this, null, null);
            } catch (ListenerNotFoundException e) {
                e.printStackTrace();
            }
            observationStopTimeMillis = System.currentTimeMillis();
    }

    public void printReport() {
        System.out.println("Garbage collector \"" + observingGC.getName() + "\" statistics:");
        System.out.println("Collections count: " + collectionsCounter);
        System.out.println("Total collections duration: " + accumulatedWorkDuration + " ms");
        long observationDurationMillis = observationStopTimeMillis - observationStartTimeMillis;
        System.out.println("Task put through time: " + ((double) observationDurationMillis / 1000) + " s");
        if(observationDurationMillis != 0 && collectionsCounter != 0) {
            System.out.println("Collections per minute: " + ((collectionsCounter * 60 * 1000) / observationDurationMillis));
            System.out.println("Stop-time per minute: " + ((accumulatedWorkDuration * 60 * 1000) / observationDurationMillis) + " ms");
            System.out.println("Average stop-time: " + accumulatedWorkDuration / collectionsCounter + " ms");
            System.out.println("Max stop-time: " + maxStopTime + " ms\n");
        }
    }

}
