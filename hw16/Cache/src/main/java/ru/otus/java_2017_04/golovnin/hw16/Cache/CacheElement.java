package ru.otus.java_2017_04.golovnin.hw16.Cache;


public class CacheElement<V> {
    private final V value;
    private final long creationTime;
    private long lastAccessTime;

    public CacheElement(V value){
        this.value = value;
        lastAccessTime = creationTime = System.currentTimeMillis();
    }

    public V getValue() {
        lastAccessTime = System.currentTimeMillis();
        return value;
    }

    public boolean isAlive(long lifeTime){
        return (System.currentTimeMillis() - creationTime) < lifeTime;
    }

    public boolean isAwake(long maxIdleTime){
        return (System.currentTimeMillis() - lastAccessTime) < maxIdleTime;
    }

    public long getIdleTime(){
        return System.currentTimeMillis() - lastAccessTime;
    }
}
