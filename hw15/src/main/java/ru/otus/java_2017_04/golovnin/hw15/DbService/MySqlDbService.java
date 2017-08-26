package ru.otus.java_2017_04.golovnin.hw15.DbService;

import ru.otus.java_2017_04.golovnin.hw15.Cache.CacheEngine;
import ru.otus.java_2017_04.golovnin.hw15.MessageSystem.MessageSystem;

import javax.sql.DataSource;
import java.util.List;

public class MySqlDbService implements DbService {
    private DataSource dataSource = (new HikariDataSourceFactory()).getDataSource();
    private UserDataSetDAO userDAO = new UserDataSetDAO(dataSource);
    private int accessCounter = 0;

    private CacheEngine<Long, UserDataSet> cache;

    public MySqlDbService(CacheEngine cache){
        this.cache = cache;
    }

    public void setMessageSystem(MessageSystem ms){
        ms.registerService("Data base", this);
    }

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
    public List<UserDataSet> getAllUsers() {
        return userDAO.getAll();
    }

    @Override
    public void removeUser(long id) {
        userDAO.delete(id);
        cache.remove(id);
    }

    @Override
    public void shutdown() {
        cache.dispose();
    }

    @Override
    public int getAccessCount(){
        return accessCounter;
    }
}
