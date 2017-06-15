package ru.otus.java_2017_04.golovnin.hw09;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "user")
@Table(name = "users")
public class User extends DataSet{
    @Column(name = "name")
    private String name;

    @Column(name = "age", nullable = false, length = 3)
    private byte age;

    public User(long id, String name, byte age){
        super(id);
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public byte getAge() {
        return age;
    }
}
