package ru.otus.java_2017_04.golovnin.hw10;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class UserDataSetDAO {
    private DataSource dataSource;
    private static final String ID_COLUMN_NAME = idColumnName();
    private static final String TABLE_NAME = getTableName();
    private static final List<Field> STORED_FIELDS = getStoredFields(UserDataSet.class);

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

    private static String getTableName(){
        Annotation tableAnnotation = UserDataSet.class.getAnnotation(Table.class);
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

    UserDataSetDAO(DataSource source){
        this.dataSource = source;
    }

    void save(UserDataSet user){
        if(user != null) {
            Map<String, String> dataMap = extractValues(user);

            String queryString;
            if (checkRecordExists(user.getId())) {
                queryString = buildUpdateStatement(TABLE_NAME, ID_COLUMN_NAME, dataMap);
            } else queryString = buildInsertStatement(TABLE_NAME, dataMap);

            DbQueryExecutor executor = new DbQueryExecutor(dataSource);
            executor.executeQuery(queryString, null);
        }
    }

    UserDataSet load(long id) {
        UserDataSet result = null;
        String queryString = buildSelectStatement(TABLE_NAME, ID_COLUMN_NAME, id);
        DbQueryExecutor executor = new DbQueryExecutor(dataSource);
        result = executor.executeQuery(queryString, new QueryResultHandler<UserDataSet>() {
            @Override
            public UserDataSet handle(ResultSet queryResult) {
                UserDataSet handleResult = null;
                try {
                    if (queryResult != null && queryResult.next()) {
                        try {
                            handleResult = UserDataSet.class.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        for (Field field:STORED_FIELDS
                                ) {
                            field.setAccessible(true);
                            String columnName = getFieldColumnName(field);
                            try {
                                field.set(handleResult, queryResult.getObject(columnName, field.getType()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            field.setAccessible(false);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return handleResult;
            }
        });
        return result;
    }

    private Map<String,String> extractValues(Object dataSet) {
        Map<String, String> dataMap = new HashMap<>();
        if(dataSet != null && STORED_FIELDS != null) {
            for (Field field : STORED_FIELDS
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



    private static List<Field> getStoredFields(Class<? extends DataSet> tClass){
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
    
    boolean checkRecordExists(long id){
        boolean result = false;
            ResultSet queryResult = null;
            try(Connection dbConnection = dataSource.getConnection()){
                StringBuilder statementBuilder = new StringBuilder();
                statementBuilder.append("select ");
                statementBuilder.append(ID_COLUMN_NAME);
                statementBuilder.append(" from ");
                statementBuilder.append(TABLE_NAME);
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

        return result;
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


}
