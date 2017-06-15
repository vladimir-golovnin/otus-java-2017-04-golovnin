package ru.otus.java_2017_04.golovnin.hw09;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class UserDataSource implements DataSource {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hw09";
    private static final Properties dbConnectionProperties;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "dbpass";

    static {
        try {
            Driver dbDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(dbDriver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbConnectionProperties = new Properties();
        dbConnectionProperties.setProperty("characterEncoding","utf8");
        dbConnectionProperties.setProperty("useUnicode","true");
        dbConnectionProperties.setProperty("useJDBCCompliantTimezoneShift","true");
        dbConnectionProperties.setProperty("useSSL", "false");
        dbConnectionProperties.setProperty("useLegacyDatetimeCode", "false");
        dbConnectionProperties.setProperty("serverTimezone", "UTC");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(USERNAME, PASSWORD);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        dbConnectionProperties.setProperty("user", username);
        dbConnectionProperties.setProperty("password", password);
        return DriverManager.getConnection(DB_URL, dbConnectionProperties);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new NotImplementedException();
    }
}
