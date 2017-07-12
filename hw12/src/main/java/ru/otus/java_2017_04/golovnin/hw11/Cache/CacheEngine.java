package ru.otus.java_2017_04.golovnin.hw11.Cache;

public interface CacheEngine<K, V> {
    void put(K key, V element);
    V get(K key);
    int getHitCount();
    int getMissCount();
    void dispose();
}
