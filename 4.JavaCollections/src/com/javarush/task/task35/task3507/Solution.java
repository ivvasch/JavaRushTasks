package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        // перебрать папку и записать все хранящиеся там файлы в list
        // из листа методом рефлексии получить у каждого объект, проверить его на наличие конструктора и принадлежность к Animal
        // поместить эти объекты в результирующий Set

        // создаем list для загрузки классов
        List<File> list = new ArrayList<>();

        //проверяем содержит ли путь в конце "/", если нет, то добавляем.
        if (pathToAnimals.endsWith("/")){
            System.out.println("it's ok");
        } else {
            pathToAnimals =  pathToAnimals.concat("/");
        }

        // считываем файлы в list
        File dir = new File(pathToAnimals);


        return null;
    }

}
