package ru.otus.java_2017_04.golovnin.hw10;


import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbServiceTest {

    @AfterClass
    public static void clearDB(){
        DataSource dataSource = (new HikariDataSourceFactory()).getDataSource();
        try (Connection dbConnection = dataSource.getConnection()){
            try(Statement statement = dbConnection.createStatement()){
                statement.execute("delete from users where true");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dbtest(){
        UserDataSet user = new UserDataSet(3329, "John Smith", 99);
        DbService dbService = new MySqlDbService();
        dbService.saveUser(user);
        UserDataSet loadedUser = dbService.loadUser(3329);
        Assert.assertNotNull(loadedUser);
        Assert.assertEquals(user.getId(), loadedUser.getId());
        Assert.assertEquals(user.getName(), loadedUser.getName());
        Assert.assertEquals(user.getAge(), loadedUser.getAge());
    }
}
