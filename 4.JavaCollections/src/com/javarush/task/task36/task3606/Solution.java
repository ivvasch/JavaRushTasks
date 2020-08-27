package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        // создаем объект File и присваиваем ему путь к папке где лежат наши классы
        File file = new File(packageName);
        // складываем все файлы из этой папки в массив
        File[] listFiles = file.listFiles();
        // перебираем все файлы в цикле и проверяем на соответствие условию
        for (File listFile : listFiles) {
            if (listFile.toString().endsWith(".class")) {
                // создаем объект MyClassLoader
                MyClassLoader loader = new MyClassLoader();
                // получаем класс из перереданного файла
                Class<?> clazz = loader.findClass(listFile.toString());
                // сохраняем полученный класс в списке
                hiddenClasses.add(clazz);
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        // извлекаем файл из списка
        for (Class hiddenClass : hiddenClasses) {
            // избавляемся от регистра и проверяем на соответствие условию
            if (hiddenClass.toString().toLowerCase().contains(key.toLowerCase())) {
                try {
                    // получаем конструктор из найденного класса
                    Constructor<?> ctr = hiddenClass.getDeclaredConstructor();
                    // разрешаем к нему доступ
                    ctr.setAccessible(true);
                    // возвращаем объект полученного класса
                    return (HiddenClass) ctr.newInstance();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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




