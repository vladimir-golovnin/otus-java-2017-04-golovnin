package ru.otus.java_2017_04.golovnin.hw15.DbService;


import javax.sql.DataSource;

public interface DataSourceFactory {
    DataSource getDataSource();
}
