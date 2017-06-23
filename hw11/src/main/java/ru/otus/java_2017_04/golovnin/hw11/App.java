package ru.otus.java_2017_04.golovnin.hw11;

import ru.otus.java_2017_04.golovnin.hw11.DbService.DbService;
import ru.otus.java_2017_04.golovnin.hw11.DbService.MySqlDbService;
import ru.otus.java_2017_04.golovnin.hw11.DbService.UserDataSet;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class App
{
    private static long startTime;
    private static long finishTime;

    public static void main( String[] args )
    {

        DbService dbService = new MySqlDbService();
        dbService.saveUser(new UserDataSet(120, "John Levi", 46));
        Random random = new Random(System.currentTimeMillis());

        for(int iteration = 0; iteration < 10; iteration++){
            long timeout = 10 + random.nextInt(2000);
            System.out.println("Timeout: " + timeout + " miliseconds");
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startMeasure();
            UserDataSet loadedUser = dbService.loadUser(120);
            finishMeasure();
            System.out.println("User #"+ loadedUser.getId() + " " +
                    loadedUser.getName() + " " + loadedUser.getAge() + " years old\n");
        }

        dbService.shutdown();
    }

    private static void startMeasure(){
        startTime = System.nanoTime();
    }

    private static void finishMeasure(){
        finishTime = System.nanoTime();
        System.out.print("Access latency ");
        System.out.print(TimeUnit.NANOSECONDS.toMicros(finishTime - startTime));
        System.out.println(" useconds");
    }
}
