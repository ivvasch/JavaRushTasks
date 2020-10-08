package com.javarush.task.task33.task3310.strategy;



public class OurHashMapStorageStrategy implements StorageStrategy {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    public int hash(Long key) {
        int h;
        return (key == null)? 0 : (h = key.hashCode()) ^ (h >>> 16 ) ;
    }

    public int indexFor(int hash, int length) {
        return hash & (length-1);
    }

    public Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash((long) key.hashCode());
        for (Entry entry = table[indexFor(hash, table.length)]; entry != null; entry = entry.next) {
            Object k;
            if (entry.hash == hash &&
                    ((k = entry.key) == key || key != null && key.equals(k)))
                return entry;
        }
        return null;
    }

    public void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == (1 << 30)) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);

    }

    public void transfer(Entry[] newTable) {
        Entry[] src = table;
        int newCapacity = newTable.length;
        for (int i = 0; i < src.length; i++) {
            Entry entry = src[i];
            if (entry != null) {
                src[i] = null;
                do {
                    Entry next = entry.next;
                    int hash = indexFor(entry.hash, newCapacity);
                    entry.next = newTable[i];
                    newTable[i] = entry;
                    entry = next;
                } while (entry != null);
            }
        }

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
