package ru.otus.java_2017_04.golovnin.hw15.DbService;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.otus.java_2017_04.golovnin.hw15.DbService.DataSourceFactory;

import javax.sql.DataSource;

public class HikariDataSourceFactory implements DataSourceFactory {
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hw09";
    private static final String USERNAME = "root";
    private static final String PASS = "dbpass";
    private static final int MAX_POOLSIZE = 8;

    private HikariConfig config = new HikariConfig();

    public HikariDataSourceFactory(){
        config.setDriverClassName(DRIVER_CLASS);
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASS);
        config.setMaximumPoolSize(MAX_POOLSIZE);
        config.addDataSourceProperty("characterEncoding","utf8");
        config.addDataSourceProperty("useUnicode","true");
        config.addDataSourceProperty("useJDBCCompliantTimezoneShift","true");
        config.addDataSourceProperty("useSSL", "false");
        config.addDataSourceProperty("useLegacyDatetimeCode", "false");
        config.addDataSourceProperty("serverTimezone", "UTC");
    }

    @Override
    public DataSource getDataSource() {
        return new HikariDataSource(config);
    }
}
