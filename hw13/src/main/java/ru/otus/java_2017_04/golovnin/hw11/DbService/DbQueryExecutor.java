package ru.otus.java_2017_04.golovnin.hw11.DbService;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQueryExecutor {
    private DataSource dataSource;

    DbQueryExecutor(DataSource dataSource){
        this.dataSource = dataSource;
    }

    <T> T executeQuery(String query, QueryResultHandler<T> handler){
        T result = null;
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            if(handler != null){
                result = handler.handle(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
