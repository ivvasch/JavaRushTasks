package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.retrievers.Retriever;
import com.javarush.task.task37.task3708.storage.Storage;
import jdk.nashorn.internal.ir.IfNode;

public class CachingProxyRetriever implements Retriever {
//    private Storage storage;
    private OriginalRetriever retriever;
    private LRUCache cache;


    public CachingProxyRetriever(Storage storage) {
        retriever = new OriginalRetriever(storage);
        cache = new LRUCache(5);
    }

    @Override
    public Object retrieve(long id) {
        Object obj = null;

        if (cache.find(id) != null) {
            return cache.find(id);
        } else {
            obj = retriever.retrieve(id);
            cache.set(id, obj);
        }
        return obj;
    }
}
