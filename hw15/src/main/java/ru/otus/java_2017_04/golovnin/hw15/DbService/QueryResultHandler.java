package ru.otus.java_2017_04.golovnin.hw15.DbService;


import java.sql.ResultSet;

@FunctionalInterface
public interface QueryResultHandler<T> {
    T handle(ResultSet queryResult);
}
