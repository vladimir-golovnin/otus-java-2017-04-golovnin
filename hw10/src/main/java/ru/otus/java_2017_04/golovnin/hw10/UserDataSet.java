package ru.otus.java_2017_04.golovnin.hw10;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "user")
@Table(name = "users")
public class UserDataSet extends DataSet{
    @Column(name = "name")
    private String name;

    @Column(name = "age", nullable = false, length = 3)
    private int age;

    UserDataSet(){
        super();
        name = "";
        age = 0;
    }

    UserDataSet(long id, String name, int age){
        super(id);
        this.name = name;
        this.age = age;
    }

    String getName(){
        return name;
    }

    int getAge() {
        return age;
    }
}
