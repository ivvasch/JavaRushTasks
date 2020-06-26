package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals){
        // перебрать папку и записать все хранящиеся там файлы в list
        // из листа методом рефлексии получить у каждого объект, проверить его на наличие конструктора и принадлежность к Animal
        // поместить эти объекты в результирующий Set
        // Создаем Set, который будем возвращать
        Set<Animal> set = new TreeSet<>();
        // создаем list для загрузки классов

        // считываем файлы в list
        File dir = new File(pathToAnimals);
        for (File files: dir.listFiles()) {
            try {
                MyClassLoader loader = new MyClassLoader();
            Class<?> clazz = loader.load(files.toString());
            Constructor<?> ctr = clazz.getConstructor();
                if (Animal.class.isAssignableFrom(clazz)) {
                    set.add((Animal) ctr.newInstance());
                }
//            Class<?>[] inter = clazz.getInterfaces();
//                for (Class<?> aClass : inter) {
//                if (aClass.getSimpleName().equals("Animal"))
//                    set.add((Animal) ctr.newInstance());
//                }

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return set;
    }
    public static class MyClassLoader extends ClassLoader {
        public Class<?> load(String name) throws ClassNotFoundException {
            byte[] bytes = new byte[0];
            try {
                bytes = Files.readAllBytes(Paths.get(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return defineClass(null, bytes, 0, bytes.length);
        }
    }

}
