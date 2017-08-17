package ru.otus.java_2017_04.golovnin.hw11.DbService;

import ru.otus.java_2017_04.golovnin.hw11.Cache.MyCache;

import javax.sql.DataSource;

public class MySqlDbService implements DbService {
    private DataSource dataSource = (new HikariDataSourceFactory()).getDataSource();
    private UserDataSetDAO userDAO = new UserDataSetDAO(dataSource);
    private int accessCounter = 0;

    public static final int CACHE_MAX_ELEMENTS = 9;
    public static final long CACHE_LIVE_TIME = 60000;
    public static final long CACHE_IDLE_TIME = 45000;

    private MyCache<Long, UserDataSet> cache = new MyCache<>(CACHE_MAX_ELEMENTS, CACHE_LIVE_TIME, CACHE_IDLE_TIME);

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

    @Override
    public int getCacheHits(){
        return cache.getHitCount();
    }

    @Override
    public int getCacheMiss(){
        return cache.getMissCount();
    }

    @Override
    public float getCacheFillFactor(){
        return cache.getFillFactor();
    }

    @Override
    public int getAccessCount(){
        return accessCounter;
    }


}
