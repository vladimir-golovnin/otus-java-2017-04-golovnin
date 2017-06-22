package ru.otus.java_2017_04.golovnin.hw10;

import javax.sql.DataSource;

public class MySqlDbService implements DbService {
    private DataSource dataSource = (new HikariDataSourceFactory()).getDataSource();
    private UserDataSetDAO userDAO = new UserDataSetDAO(dataSource);


    @Override
    public void saveUser(UserDataSet user) {
        userDAO.save(user);
    }

    @Override
    public UserDataSet loadUser(long id) {
        return userDAO.load(id);
    }
}
