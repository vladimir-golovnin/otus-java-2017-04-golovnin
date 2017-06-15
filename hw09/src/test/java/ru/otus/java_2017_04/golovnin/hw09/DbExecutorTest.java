package ru.otus.java_2017_04.golovnin.hw09;


import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;

public class DbExecutorTest {

    private DataSource dataSource = new UserDataSource();
    private DbExecutor executor = new DbExecutor(dataSource);

    @Test
    public void testSave(){
        User testUser = new User(19, "David", (byte) 88);
        executor.save(testUser);

        try (Connection dbConnection = dataSource.getConnection()){
            try(Statement statement = dbConnection.createStatement()){
                ResultSet result = statement.executeQuery("select * from users where id=" + testUser.getId());
                Assert.assertTrue(result.next());
                Assert.assertEquals(testUser.getId(), result.getInt("id"));
                Assert.assertEquals(testUser.getName(), result.getString("name"));
                Assert.assertEquals(testUser.getAge(), result.getByte("age"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoad(){
        executor.load(13, User.class);
    }

    @Test
    public void testExist(){
        try(Connection dbConnection = dataSource.getConnection()){
            try(Statement statement = dbConnection.createStatement()){
                statement.execute("insert into users (id, name, age) values ('18', 'John', '36')");
                Assert.assertTrue(executor.checkRecordExists(18, User.class));
                statement.execute("delete from users where id=18");
                Assert.assertFalse(executor.checkRecordExists(18,User.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFields(){
        User testUser = new User(18, "Fred", (byte)56);
        try {
            Field idField = DataSet.class.getDeclaredField("id");
            idField.setAccessible(true);
            Assert.assertEquals(18,idField.getLong(testUser));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
