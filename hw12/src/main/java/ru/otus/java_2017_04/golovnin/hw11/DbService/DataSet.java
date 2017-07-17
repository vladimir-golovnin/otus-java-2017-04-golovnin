package ru.otus.java_2017_04.golovnin.hw11.DbService;


import javax.persistence.*;

@MappedSuperclass
public class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, length = 20)
    private long id;

    DataSet(){
        id = -1;
    }

    DataSet(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }
}
