package ru.otus.java_2017_04.golovnin.hw09;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;

public class DbExecutorTest {

    private static final DataSource dataSource = new UserDataSource();
    private DbExecutor executor = new DbExecutor(dataSource);

    @BeforeClass
    public static void clearDB(){
        try (Connection dbConnection = dataSource.getConnection()){
            try(Statement statement = dbConnection.createStatement()){
                statement.execute("delete from users where true");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSave(){
        User testUser = new User(19, "David", 88);
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
        User sampleUser = new User(1, "Peter", 49);
        Assert.assertNull(executor.load(sampleUser.getId(), User.class));
        try (Connection dbConnection = dataSource.getConnection()){
            try(Statement statement = dbConnection.createStatement()){
                statement.execute("insert into users (id, name, age) values ('" +
                        sampleUser.getId() + "', '" + sampleUser.getName() + "', '" + sampleUser.getAge() + "')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User loadedUser = executor.load(sampleUser.getId(), User.class);

        Assert.assertNotNull(loadedUser);
        Assert.assertEquals(sampleUser.getId(), loadedUser.getId());
        Assert.assertEquals(sampleUser.getName(), loadedUser.getName());
        Assert.assertEquals(sampleUser.getAge(), loadedUser.getAge());

        System.out.println("User id: " + loadedUser.getId());
        System.out.println("User name: " + loadedUser.getName());
        System.out.println("User age: " + loadedUser.getAge());
    }
}
