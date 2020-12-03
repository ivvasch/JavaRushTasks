package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
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
        Map<String, Integer> romanMap = new HashMap<String, Integer>() {{
            put("I", 1);
            put("V", 5);
            put("X", 10);
            put("L", 50);
            put("C", 100);
            put("D", 500);
            put("M", 1000);
        }};
        String[] roman = s.split("");
        int result = 0;
        if (romanMap.get(roman[roman.length - 2]) >= romanMap.get(roman[roman.length - 1])) {
            result = romanMap.get(roman[roman.length - 2]) + romanMap.get(roman[roman.length - 1]);
        } else {
            result = romanMap.get(roman[roman.length - 1]) - romanMap.get(roman[roman.length - 2]);
        }
        if (roman.length > 2) {
            for (int i = roman.length - 2; i >= 0; i--) {
                if (i == 0) {
                    result = result + romanMap.get(roman[i]);
                } else if (romanMap.get(roman[i - 1]) >= romanMap.get(roman[i])) {
                    result = romanMap.get(roman[i-1]) + result;
                    if ((i - 1) == 0) {
                        return result;
                    }

                } else {
                    result = result - romanMap.get(roman[i - 1]);
                    if ((i - 1) == 0) {
                        return result;
                    }
                }

            }
        }
        return result;
    }
}
