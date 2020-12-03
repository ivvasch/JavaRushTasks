package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
Древний Рим
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        String romanNumeral = s.toUpperCase();
        int result = 0;

        List<Roman> romanList = Roman.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanList.size())) {
            Roman roman = romanList.get(i);
            System.out.println(romanList.get(i));
            if (romanNumeral.startsWith(roman.name())) {
                System.out.println(roman.getValue());
                result = result + roman.getValue();
                romanNumeral = romanNumeral.substring(roman.name().length());
            } else {
                i++;
            }
        }
        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(s + " cannot be converted");

        }
        return result;
    }
}
