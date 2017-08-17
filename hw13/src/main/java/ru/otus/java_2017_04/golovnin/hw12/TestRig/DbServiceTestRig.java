package ru.otus.java_2017_04.golovnin.hw12.TestRig;

import ru.otus.java_2017_04.golovnin.hw11.DbService.MySqlDbService;
import ru.otus.java_2017_04.golovnin.hw11.DbService.UserDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class DbServiceTestRig implements ITestRig{
    private long startTime;
    private int iteration;
    private static final int ITERATIONS_NUM = 1_000_000;
    private static final MySqlDbService dbService = new MySqlDbService();
    private static final List<UserDataSet> usersList = new ArrayList<>();


    public DbServiceTestRig(){
        usersList.add(new UserDataSet(1, "Frank", 19));
        usersList.add(new UserDataSet(2, "John", 42));
        usersList.add(new UserDataSet(3, "Robin", 34));
        usersList.add(new UserDataSet(4, "Tom", 21));
        usersList.add(new UserDataSet(5, "Lloyd", 31));
        usersList.add(new UserDataSet(6, "Rick", 66));
        usersList.add(new UserDataSet(7, "Ian", 24));
        usersList.add(new UserDataSet(8, "Eddy", 57));
        usersList.add(new UserDataSet(9, "Neil", 14));
        usersList.add(new UserDataSet(10, "Mick", 49));
        usersList.add(new UserDataSet(11, "Ted", 26));

        for (UserDataSet user:usersList
                ) {
            dbService.saveUser(user);
        }
    }

    @Override
    public void start()
    {
        Random random = new Random(System.currentTimeMillis());

        final int MIN_TIMEOUT = 10;
        final int TIMEOUT_DISPERSION = 1000;

        for(iteration = 0; iteration < ITERATIONS_NUM; iteration++){
            long timeout = MIN_TIMEOUT + random.nextInt(TIMEOUT_DISPERSION);
            System.out.println("Timeout: " + timeout + " miliseconds");
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int userIndex = random.nextInt(usersList.size());

            startMeasure();
            UserDataSet loadedUser = dbService.loadUser(usersList.get(userIndex).getId());
            finishMeasure();
            System.out.println("User #"+ loadedUser.getId() + " " +
                    loadedUser.getName() + " " + loadedUser.getAge() + " years old\n");
        }

        dbService.shutdown();
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new TreeMap<>();
        parameters.put("Iteration count", iteration);
        parameters.put("Access count", dbService.getAccessCount());
        parameters.put("Cache hits", dbService.getCacheHits());
        parameters.put("Cache misses", dbService.getCacheMiss());
        parameters.put("Cache fill factor", dbService.getCacheFillFactor());
        parameters.put("Cache elements max count", MySqlDbService.CACHE_MAX_ELEMENTS);
        parameters.put("Cache live time, ms", MySqlDbService.CACHE_LIVE_TIME);
        parameters.put("Cache idle time, ms", MySqlDbService.CACHE_IDLE_TIME);
        return parameters;
    }

    private void startMeasure(){
        startTime = System.nanoTime();
    }

    private void finishMeasure(){
        long finishTime = System.nanoTime();
        System.out.print("Access latency ");
        System.out.print(TimeUnit.NANOSECONDS.toMicros(finishTime - startTime));
        System.out.println(" useconds");
    }
}
