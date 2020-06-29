package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
        /* перебрать папку с файлами,
        методом рефлексии получить у каждого объект, проверить его на наличие конструктора и принадлежность к Animal,
        поместить эти объекты в результирующий Set */

        // Создаем Set, который будем возвращать
        Set<Animal> set = new HashSet<>();
        // перебираем файлы, для этого создаем объект File и передаем в него путь
        File dir = new File(pathToAnimals);
        for (File files: dir.listFiles()) {
            try {
                // создаем отдельный класс MyClassLoader, создаем объект этого класса
                MyClassLoader loader = new MyClassLoader();
                // передаем имя класса в метод findClass класса MyClassLoader
            Class<?> clazz = loader.findClass(files.toString());
                // получаем публичный конструктор полученного класса
            Constructor<?> ctr = (clazz.getConstructor());
                // если конструктор есть, то у нас не выскакивает исключение и мы создаем объект этого класса, кастим его к Animal и добавляем его в set
                    set.add((Animal) ctr.newInstance());
                // обрабатываем исключения
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return set;
    }
    // создаем свой загрузчик классов и наследуемся от основного загрузчика
    public static class MyClassLoader extends ClassLoader {
    // переопределяем метод и передаем в него полученный из нашей директории класс
        public Class<?> findClass(String name) throws ClassNotFoundException {
    // создаем массив байт для считывания в него файла
            byte[] bytes = new byte[0];
            try {
    // считываем в него файл
                bytes = Files.readAllBytes(Paths.get(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
    // с помощью метода defineClass загружаем и возвращаем класс
            return defineClass(null, bytes, 0, bytes.length);
        }
    }

}
