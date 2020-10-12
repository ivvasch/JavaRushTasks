package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;

public class FileStorageStrategy implements StorageStrategy {
    // размер по умолчанию нашего FileBucket
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    // максимальный размер одной корзины
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 1000l;
    // создаем корзину с размером по умолчанию
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    // счетчик Entry
    private int size;
    // размер бакета, после которого увеличивается наш FileBucket
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    // loadfactor
    long maxBucketSize;

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

    // увеличение нашего FileBucket
    public void resize(int newCapacity) throws IOException, ClassNotFoundException {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
    }
    //-----------------------------------------------

    // перемещаем все Entry в увеличенный FileBucket
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
                try {
                    table[i].remove();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // ----------------------------------------------------------------

    // добавляем новый Entry
    public void addEntry(int hash, Long key, String value, int bucketIndex) throws IOException {
        Entry entry = null;
        try {
            entry = table[bucketIndex].getEntry();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        table[bucketIndex].putEntry(new Entry(hash, key, value, entry));
        size++;

            try {
        if (table[bucketIndex].getFileSize() >= bucketSizeLimit) {
            resize(2 * table.length);
        }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
    // --------------------------------------------------------------------

    // создаем новый Entry
    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        try {
            table[bucketIndex] = new FileBucket();
            table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        size++;
    }
    // ----------------------------------------------------------------------

    // уточняем содержится ли переданный ключ в бакете
    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }
    // ----------------------------------------------------------------------

    // уточняем содержится ли value в бакете
    @Override
    public boolean containsValue(String value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null)
                continue;

            Entry entry = null;
            try {
                entry = table[i].getEntry();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            while (entry != null) {
                if (entry.value.equals(value))
                    return true;

                entry = entry.next;
            }
        }
        return false;
    }
    // ---------------------------------------------------------------------------

    // кладем Entry по ключу и значению
    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
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
                    entry.value = value;
                    return;
                }
                entry = entry.next;
            }
            try {
                addEntry(hash, key, value, index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            createEntry(hash, key, value, index);
        }
    }
    // -----------------------------------------------------------------------

    // получаем ключ по значению
    @Override
    public Long getKey(String value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null)
                continue;
            Entry entry = null;
            if (table[i] != null) {
                try {
                    entry = table[i].getEntry();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                while (entry != null) {
                    if (entry.value.equals(value)) {
                        return entry.key;
                    }
                    entry = entry.next;
                }
            }
        }
        return 0l;
    }
    // --------------------------------------------------------------------

    // получаем значение по ключу
    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);
        if (entry != null)
            return entry.value;

        return null;
    }
    // --------------------------------------------------------------------
}