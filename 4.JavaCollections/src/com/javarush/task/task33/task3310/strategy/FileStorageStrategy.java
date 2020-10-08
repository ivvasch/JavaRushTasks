package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;

public class FileStorageStrategy implements StorageStrategy {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 1000l;
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize; // loadfactor

    // геттеры и сеттеры
    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }
    // --------------------------------------------

    // вычисляем хэшкод
    public int hash(Long key) {
        int h;
        return (key == null)? 0 : (h = key.hashCode()) ^ (h >>> 16 ) ;
    }
    // --------------------------------------------

    // вычисляем индекс
    public int indexFor(int hash, int length) {
        return hash & (length-1);
    }
    // --------------------------------------------

    // находим Entry
    public Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash((long) key.hashCode());
        int index = indexFor(hash, table.length);
        if (table[index] != null) {
            Entry entry = null;
            try {
                entry = table[index].getEntry();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            while (entry != null) {
                if (entry.getKey().equals(key)) {
                    return entry;
                }
                entry = entry.next;
            }
        }
        return null;
    }
    // ----------------------------------------------

    public void resize(int newCapacity) throws IOException, ClassNotFoundException {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
    }

    public void transfer(FileBucket[] newTable){
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                Entry entry = null;
                try {
                    entry = table[i].getEntry();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                while (entry != null) {
                    Entry next = entry.next;
                    int newIndex = indexFor(entry.hash, newTable.length);
                    if (newTable[newIndex] == null) {
                        entry.next = null;
                        newTable[newIndex] = new FileBucket();
                    } else {
                        try {
                            entry.next = newTable[newIndex].getEntry();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        newTable[newIndex].putEntry(entry);
                        entry = next;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                table[i].remove();
            }
        }
//        FileBucket[] src = table;
//        int newCapacity = newTable.length;
//        for (int i = 0; i < src.length; i++) {
//            FileBucket fileBucket = src[i];
//            if (fileBucket != null) {
//                src[i] = null;
//                do {
//                    Entry next = fileBucket.getEntry().next;
//                    int hash = indexFor(fileBucket.getEntry().hash, newCapacity);
//                    fileBucket = newTable[i];
//                    newTable[i] = fileBucket;
//                    fileBucket = next;
//                } while (fileBucket != null);
//            }
//        }

    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, entry);
        if (size++ >= threshold) {
            resize(2* table.length);
        }
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, entry);
        size++;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        Entry[] tab = table;
        for (int i = 0; i < table.length; i++)
            for (Entry entry = tab[i]; entry != null; entry = entry.next)
                if (value.equals(entry.value))
                    return true;
        return false;
    }

    @Override
    public void put(Long key, String value) {
        // проверить Entry[] table на наличие похожего если есть заменить, если нет создать createEntry + addEntry
        addEntry(hash(key), key, value, indexFor(hash(key),table.length));
    }

    @Override
    public Long getKey(String value) {
        if (value == null) {
            return null;
        }
        for (int i = 0; i < table.length; i++) {
            Entry entry = table[i];
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        return null == getEntry(key)? null : getEntry(key).getValue();
    }
}