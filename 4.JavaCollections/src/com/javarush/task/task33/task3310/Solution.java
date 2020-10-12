package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.io.IOException;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
//        FileBucket fileBucket = new FileBucket();
//        System.out.println("size " + fileBucket.getFileSize());
//        HashMapStorageStrategy map = new HashMapStorageStrategy();
        OurHashMapStorageStrategy ourMap = new OurHashMapStorageStrategy();
        testStrategy(ourMap, 10000);
//        testStrategy(map, 10000);
        FileStorageStrategy fileStorageStrategy = new FileStorageStrategy();
        testStrategy(fileStorageStrategy, 100);

    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> set = new HashSet<>();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            set.add(shortener.getId(iterator.next()));
        }
        return set;
    }
    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> setString = new HashSet<>();
        Iterator<Long> iterator = keys.iterator();
        while (iterator.hasNext()) {
            setString.add(shortener.getString(iterator.next()));
        }
        return setString;

    }
    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Set<String> str = new HashSet<>();
        //6.2.3.1
        System.out.println(strategy.getClass().getSimpleName());
        //6.2.3.2
        for (int i = 0; i < elementsNumber; i++) {
            str.add(Helper.generateRandomString());
        }
        //6.2.3.3
        Shortener shortener = new Shortener(strategy);
        //6.2.3.4
        Long fistDate = new Date().getTime();
        Set<Long> keys = getIds(shortener, str);
        Long lastDate = new Date().getTime();
        System.out.println("getIds " + (lastDate - fistDate));
        //6.2.3.5
        Long thirdDate = new Date().getTime();
        Set<String> strings = getStrings(shortener, keys);
        Long forthDate = new Date().getTime();
        System.out.println("getStrings " + (forthDate - thirdDate));
        //6.2.3.6 str == strings?????
        if (str.containsAll(strings)) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }


    }


}
