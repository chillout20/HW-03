package hw3;

import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {
    public static class Pair<K, V> {
        private K key;
        private V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private int bucketSize = 16;
    private LinkedList<Pair<K, V>>[] buckets = new LinkedList[bucketSize];

    public MyHashMap() {
        clear();
    }

    @Override
    public int size() {
        int result = 0;

        for (int i = 0; i < buckets.length; i++) {
            result += buckets[i].size();
        }

        return result;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private int keyToBucketIndex(Object key) {
        return Math.abs(key.hashCode() % bucketSize);
        //return key.hashCode() >> 27;
    }

    @Override
    public boolean containsKey(Object key) {
        int i = keyToBucketIndex(key);
        for (Pair<K, V> pair: buckets[i]) {
            if (pair.key.equals(key)) return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i=0; i < bucketSize; i++) {
            if (buckets[i].size() == 0) continue;
            for (Pair<K, V> pair: buckets[i]){
                if (pair.value.equals(value)) return true;
            }
        }
        return false;
    }

    // Could you please let me know why this doesn't work?

    //public boolean containsValue(Object value) {
    //    for (LinkedList lls : buckets) {
    //        if (lls.size() == 0) continue;
    //        for (Pair<K, V> pair: lls){
    //            if (pair.key.equals(value)) return true;
    //        }
    //    }
    //    return false;
    //}

    @Override
    public V get(Object key) {
        // Only return value of firstly occurred pair (in case of multiple pair)
        int i = keyToBucketIndex(key);
        for (Pair<K, V> pair : buckets[i]) {
            if (pair.key.equals(key)) return pair.value;
        }
        System.out.println("Key '" + key + "' does not exist");
        return null;
    }

    @Override
    public V put(K key, V value) {
        Pair<K, V> pair = new Pair<>(key, value);
        int i = keyToBucketIndex(key);

        if (buckets[i].contains(pair)) {
            // replace it!
        } else {
            buckets[i].add(pair);
        }

        return value;
    }

    @Override
    public V remove(Object key) {
        // Remove all pair with 'key' (in case of multiple pair)
        int i = keyToBucketIndex(key);
        buckets[i].removeIf(pair -> pair.key.equals(key));
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Set<? extends Entry<? extends K, ? extends V>> entrySet = m.entrySet();
        for (Entry<? extends K, ? extends V> entry : entrySet) {
            put((K) entry.getKey(), (V) entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
