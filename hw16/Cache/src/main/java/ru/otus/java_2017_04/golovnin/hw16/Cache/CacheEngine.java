package ru.otus.java_2017_04.golovnin.hw16.Cache;

public interface CacheEngine<K, V> {
    void put(K key, V element);
    void remove(K key);
    V get(K key);
    int getHitCount();
    int getMissCount();
    float getFillFactor();

    void dispose();
}
