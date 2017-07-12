package ru.otus.java_2017_04.golovnin.hw11.Cache;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<K, V> implements CacheEngine<K, V> {
    private final int maxElements;
    private final long lifeTimeMillis;
    private final long idleTimeMillis;
    private Map<K, SoftReference<CacheElement<V>>> elementsMap;
    private int hitCounter;
    private int missCounter;
    private static  final int MAX_ELEMENTS_DEFAULT = 7;
    private static final long PURGE_PERIOD = 10;
    private Timer purgeTimer;

    public Cache(int maxElements, long lifeTime, long idleTime){
        this.maxElements = maxElements > 0 ? maxElements : MAX_ELEMENTS_DEFAULT;
        this.lifeTimeMillis = lifeTime;
        this.idleTimeMillis = idleTime;
        elementsMap = new ConcurrentHashMap<>(maxElements);

        purgeTimer = new Timer(true);
        purgeTimer.schedule(new PurgeTask(), PURGE_PERIOD, PURGE_PERIOD);
    }

    private class PurgeTask extends TimerTask{
        @Override
        public void run() {
            synchronized (elementsMap){
                Iterator<K> keyIterator = elementsMap.keySet().iterator();
                while (keyIterator.hasNext()) {
                    K processedKey = keyIterator.next();
                    CacheElement<V> element = elementsMap.get(processedKey).get();
                    if(element != null) {
                        if ((lifeTimeMillis > 0 && !element.isAlive(lifeTimeMillis)) ||
                                (idleTimeMillis > 0 && !element.isAwake(idleTimeMillis))) {
                            elementsMap.remove(processedKey);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void put(K key, V element) {
        if (element != null) {
            synchronized (elementsMap) {
                if (elementsMap.size() == maxElements && elementsMap.get(key) == null) {
                    clearSpace();
                }
                elementsMap.put(key, new SoftReference<>(new CacheElement<>(element)));
            }
        }
    }

    private void clearSpace(){
        long idleTimeMax = 0;
        K uselessKey = null;
        Iterator<K> keyIterator = elementsMap.keySet().iterator();
        while (keyIterator.hasNext()) {
            K processedKey = keyIterator.next();
            CacheElement<V> element = elementsMap.get(processedKey).get();
            if(element != null) {
                long elementIdleTime = element.getIdleTime();
                if(idleTimeMax < elementIdleTime){
                    idleTimeMax = elementIdleTime;
                    uselessKey = processedKey;
                }
            }
        }
        elementsMap.remove(uselessKey);
    }

    @Override
    public V get(K key) {
        V element = null;
        synchronized (elementsMap) {
            SoftReference<CacheElement<V>> reference = elementsMap.get(key);
            if (reference != null) {
                CacheElement<V> cacheElement = reference.get();
                if (cacheElement != null) {
                    element = cacheElement.getValue();
                } else elementsMap.remove(key);
            }
        }
        if(element == null) missCounter++;
        else hitCounter++;
        return element;
    }

    @Override
    public int getHitCount() {
        return hitCounter;
    }

    @Override
    public int getMissCount() {
        return missCounter;
    }

    @Override
    public void dispose() {
        purgeTimer.cancel();
    }
}
