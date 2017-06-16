package ru.otus.java_2017_04.golovnin.hw09;

import javax.persistence.*;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class DbExecutor {
    private DataSource dataSource;
    private static final String ID_COLUMN_NAME = idColumnName();

    DbExecutor(DataSource source){
        this.dataSource = source;
    }

    <T extends DataSet> void save(T dataSet){
        if(dataSet != null) {
            String tableName = getTableName(dataSet.getClass());
            if (tableName != null) {
                Map<String, String> dataMap = extractValues(dataSet, getStoredFields(dataSet.getClass()));

                String statementString;
                if (checkRecordExists(dataSet.getId(), dataSet.getClass())) {
                    statementString = buildUpdateStatement(tableName, ID_COLUMN_NAME, dataMap);
                } else statementString = buildInsertStatement(tableName, dataMap);

                try (Connection dbConnection = dataSource.getConnection()) {
                    Statement statement = dbConnection.createStatement();
                    statement.execute(statementString);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Map<String,String> extractValues(Object dataSet, List<Field> fieldsList) {
        Map<String, String> dataMap = new HashMap<>();
        if(dataSet != null && fieldsList != null) {
            for (Field field : fieldsList
                    ) {
                field.setAccessible(true);
                try {
                    dataMap.put(field.getName(), field.get(dataSet).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
            }
        }
        return dataMap;
    }

    private String buildInsertStatement(String tableName, Map<String, String> valuesMap){
        StringBuilder statementBuilder = new StringBuilder("insert into ");
        statementBuilder.append(tableName);
        statementBuilder.append(" (");
        for (String columnName:valuesMap.keySet()
                ) {
            statementBuilder.append(columnName);
            statementBuilder.append(",");
        }
        statementBuilder.replace(statementBuilder.length()-1, statementBuilder.length(), ") values (");
        for (String columnName:valuesMap.keySet()
                ) {
            statementBuilder.append("'");
            statementBuilder.append(valuesMap.get(columnName));
            statementBuilder.append("',");
        }
        statementBuilder.replace(statementBuilder.length()-1, statementBuilder.length(), ")");
        return statementBuilder.toString();
    }

    private String buildUpdateStatement(String tableName, String idColumnName, Map<String, String> valuesMap){
        StringBuilder statementBuilder = new StringBuilder("update ");
        statementBuilder.append(tableName);
        statementBuilder.append(" set ");

        for(Map.Entry<String, String> entry:valuesMap.entrySet()){
            statementBuilder.append(entry.getKey());
            statementBuilder.append("='");
            statementBuilder.append(entry.getValue());
            statementBuilder.append("', ");
        }
        statementBuilder.replace(statementBuilder.length()-2, statementBuilder.length(), " where ");
        statementBuilder.append(idColumnName);
        statementBuilder.append("=");
        statementBuilder.append(valuesMap.get(idColumnName));
        return statementBuilder.toString();
    }

    private String buildSelectStatement(String tableName, String idColumnName, long id){
        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("select * from ");
        statementBuilder.append(tableName);
        statementBuilder.append(" where ");
        statementBuilder.append(idColumnName);
        statementBuilder.append("=");
        statementBuilder.append(id);
        return statementBuilder.toString();
    }

    <T extends DataSet> T load(long id, Class<T> tClass) {
        T result = null;
        if(tClass != null) {
            String tableName = getTableName(tClass);
            if (tableName != null) {

                ResultSet queryResult;
                try (Connection dbConnection = dataSource.getConnection()) {
                    Statement statement = dbConnection.createStatement();
                    String queryString = buildSelectStatement(tableName, ID_COLUMN_NAME, id);
                    queryResult = statement.executeQuery(queryString);
                    if (queryResult != null && queryResult.next()) {
                        try {
                            result = tClass.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        List<Field> storedFields = getStoredFields(tClass);
                        for (Field field:storedFields
                             ) {
                            field.setAccessible(true);
                            String columnName = getFieldColumnName(field);
                            try {
                                field.set(result, queryResult.getObject(columnName, field.getType()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            field.setAccessible(false);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private List<Field> getStoredFields(Class<? extends DataSet> tClass){
        List<Field> fieldsList = new LinkedList<>();
        if(tClass != null) {
            if (tClass.getAnnotation(Entity.class) != null ||
                    tClass.getAnnotation(MappedSuperclass.class) != null) {
                for (Field field : tClass.getDeclaredFields()
                        ) {
                    if(getFieldColumnName(field) != null)
                        fieldsList.add(field);
                }
                if(tClass.getAnnotation(MappedSuperclass.class) == null){
                    Class superclass = tClass.getSuperclass();
                    if(superclass != null) {
                        fieldsList.addAll(getStoredFields(superclass));
                    }
                }
            }
        }
        return fieldsList;
    }
    
    boolean checkRecordExists(long id, Class<? extends DataSet> tClass){
        boolean result = false;
        String tableName = getTableName(tClass);
        if(tableName != null){
            ResultSet queryResult = null;
            try(Connection dbConnection = dataSource.getConnection()){
                StringBuilder statementBuilder = new StringBuilder();
                statementBuilder.append("select ");
                statementBuilder.append(ID_COLUMN_NAME);
                statementBuilder.append(" from ");
                statementBuilder.append(tableName);
                statementBuilder.append(" where ");
                statementBuilder.append(ID_COLUMN_NAME);
                statementBuilder.append("=");
                statementBuilder.append(id);
                Statement statement = dbConnection.createStatement();
                queryResult = statement.executeQuery(statementBuilder.toString());
                if (queryResult != null){
                    result = queryResult.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String idColumnName(){
        String name = null;
        Field idField = null;
        try {
            idField = DataSet.class.getDeclaredField("id");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if(idField != null){
            name = getFieldColumnName(idField);
        }
        return name;
    }

    private static String getFieldColumnName(Field field){
        String name = null;
        Annotation columnAnnotation = field.getAnnotation(Column.class);
        if(columnAnnotation != null){
            try {
                Method nameMethod = Column.class.getMethod("name");
                name = (String)nameMethod.invoke(columnAnnotation);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    private <T extends DataSet> String getTableName(Class<T> tClass){
        Annotation tableAnnotation = tClass.getAnnotation(Table.class);
        String tableName = null;
        try {
            Method nameMethod = Table.class.getMethod("name");
            tableName = (String)nameMethod.invoke(tableAnnotation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return tableName;
    }
}
