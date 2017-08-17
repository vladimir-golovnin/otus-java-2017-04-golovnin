package ru.otus.java_2017_04.golovnin.hw11.DbService;

import ru.otus.java_2017_04.golovnin.hw11.Cache.Cache;

import javax.sql.DataSource;

public class MySqlDbService implements DbService {
    private DataSource dataSource = (new HikariDataSourceFactory()).getDataSource();
    private UserDataSetDAO userDAO = new UserDataSetDAO(dataSource);
    private int accessCounter = 0;

    public static final int CACHE_MAX_ELEMENTS = 9;
    public static final long CACHE_LIVE_TIME = 60000;
    public static final long CACHE_IDLE_TIME = 45000;

    private Cache<Long, UserDataSet> cache = new Cache<>(CACHE_MAX_ELEMENTS, CACHE_LIVE_TIME, CACHE_IDLE_TIME);

    @Override
    public void saveUser(UserDataSet user) {
        userDAO.save(user);
    }

    @Override
    public UserDataSet loadUser(long id) {
        accessCounter++;
        UserDataSet loadedUser = null;
        if((loadedUser = cache.get(id)) == null){
            loadedUser = userDAO.load(id);
            if(loadedUser != null){
                cache.put(id, loadedUser);
            }
        }
        return loadedUser;
    }

    @Override
    public void shutdown() {
        cache.dispose();
    }

    public int getCacheHits(){
        return cache.getHitCount();
    }

    public int getCacheMiss(){
        return cache.getMissCount();
    }

    public float getCacheFillFactor(){
        return cache.getFillFactor();
    }

    public int getAccessCount(){
        return accessCounter;
    }


}
