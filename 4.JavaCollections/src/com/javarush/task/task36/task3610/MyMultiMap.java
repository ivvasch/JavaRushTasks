package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int total = 0;
        for (List<V> list : map.values()) {
            total += list.size();
        }
        return total;
    }

    @Override
    public V put(K key, V value) {
        if (!map.containsKey(key)) {
            ArrayList<V> list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
            return null;
        } else {
            ArrayList<V> listOfV  = (ArrayList<V>) this.map.get(key);
            V x = listOfV.get(listOfV.size() - 1);
            if (listOfV.size() == repeatCount) {
                listOfV.remove(0);
                listOfV.add(value);
                map.put(key, listOfV);
                return x;
            } else {
                listOfV.add(value);
                map.put(key, listOfV);
                return x;
            }
        }
    }

    @Override
    public V remove(Object key) {
        if (!map.containsKey(key)) {
            return null;
        } else {
            List<V> list = map.get(key);
            Object x = list.get(0);
            list.remove(0);
            if (list.size() == 0) {
                map.remove(key);
            } else {
                map.put((K) key, list);
            }
            return (V) x;
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> hashSet = new HashSet<>();
        for (Entry<K, List<V>> pair : map.entrySet()) {
            hashSet.add(pair.getKey());
        }
        return hashSet;
    }

    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()){
            for (V v : entry.getValue()){
                list.add(v);
            }
        }

        return list;
    }

    @Override
    public boolean containsKey(Object key) {
        if (map.containsKey(key)) {
            return true;
        } else return false;
    }

    @Override
    public boolean containsValue(Object value) {
        ArrayList<V> ls = new ArrayList<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            for (V v : entry.getValue()) {
                ls.add(v);
            }
        }
        return ls.contains(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}