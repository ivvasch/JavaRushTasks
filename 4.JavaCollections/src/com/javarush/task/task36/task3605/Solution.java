package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        Set<String> sortSet = new TreeSet<>();
        File file = new File(args[0]);
        FileReader fileReader = new FileReader(file);
        String c;
        int a = fileReader.read();
        while (a != -1) {
            if (a < 90 & a > 65) {
               c = String.valueOf((char)a).toLowerCase();
                sortSet.add(c);
            } else if (a > 96 & a < 123) {
                c = String.valueOf((char)a);
                sortSet.add(c);
            }
            a = fileReader.read();
        }
        fileReader.close();
        int count = 0;
        for (String s : sortSet) {
            if (count == 5) {
                break;
            }
            System.out.print(s);
            count++;
        }
    }
}
