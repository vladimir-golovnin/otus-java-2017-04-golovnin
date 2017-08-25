package ru.otus.java_2017_04.golovnin.hw15.Cache;

public interface CacheEngine<K, V> {
    void put(K key, V element);
    V get(K key);
    int getHitCount();
    int getMissCount();
    float getFillFactor();

    void dispose();
}
