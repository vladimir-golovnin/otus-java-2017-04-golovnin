package ru.otus.java_2017_04.golovnin.hw09;


import java.sql.*;
import java.util.Properties;

public class App
{
    public static void main( String[] args )
    {
        try {
            Driver dbDriver = new com.mysql.cj.jdbc.Driver();

            DriverManager.registerDriver(dbDriver);
            Properties dbConnectionProperties = new Properties();
            dbConnectionProperties.setProperty("characterEncoding","utf8");
            dbConnectionProperties.setProperty("useUnicode","true");
            dbConnectionProperties.setProperty("useJDBCCompliantTimezoneShift","true");
            dbConnectionProperties.setProperty("useSSL", "false");
            dbConnectionProperties.setProperty("useLegacyDatetimeCode", "false");
            dbConnectionProperties.setProperty("serverTimezone", "UTC");
            dbConnectionProperties.setProperty("user", "root");
            dbConnectionProperties.setProperty("password", "dbpass");

            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw09", dbConnectionProperties);
            try(Statement statement = dbConnection.createStatement()){
                statement.execute("insert into users (name, age) values ('Вася', '32')");
                ResultSet result = statement.executeQuery("select * from users");
                while (result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    byte age = result.getByte("age");
                    System.out.println(id + " " + name + ", " + age);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
