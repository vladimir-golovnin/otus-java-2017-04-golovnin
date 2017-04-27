package ru.otus.java_2017_04.golovnin.hw04;


import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        List<GCSpy> list = GCSpyFactory.getGCSpies();
        list.forEach(GCSpy::startObservation);
        new DrippingBucket().fill();
        list.forEach(GCSpy::stopObservation);
        list.forEach(GCSpy::printReport);

    }

    
}
