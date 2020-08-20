package com.javarush.task.task36.task3611;

import java.util.Set;
import java.util.TreeSet;

/*
Сколько у человека потенциальных друзей?
*/

public class Solution {
    private boolean[][] humanRelationships;

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.humanRelationships = generateRelationships();

        Set<Integer> allFriendsAndPotentialFriends = solution.getAllFriendsAndPotentialFriends(4, 2);
        System.out.println(allFriendsAndPotentialFriends);                              // Expected: [0, 1, 2, 3, 5, 7]
        Set<Integer> potentialFriends = solution.removeFriendsFromSet(allFriendsAndPotentialFriends, 4);
        System.out.println(potentialFriends);                                           // Expected: [2, 5, 7]
    }

    public Set<Integer> getAllFriendsAndPotentialFriends(int index, int deep) {
        // результирующий сет
        Set<Integer> resultSet = new TreeSet<>();
        // временный сет
        Set<Integer> tempSet = new TreeSet<>();
        // временный сет для глубины
        Set<Integer> tempSet2 = new TreeSet<>();
        // если глубина равна 1 то добавляем первую линию друзей в результирующий сет и возвращаем его
        if (deep == 1) {
            for (int i = 0; i < humanRelationships.length; i++) {
                if ((i < index) && (index < humanRelationships.length) && humanRelationships[index][i]) {
                    resultSet.add(i);
                } else if ((i > index) && humanRelationships[i][index]) {
                    resultSet.add(i);
                }
            }
            return resultSet;
        }
        // при глубине более 1 ищем друзей у index, добавляем их во временный сет и перебираем их
        if (deep > 1) {
            for (int l = 0; l < humanRelationships.length; l++) {
                if ((l < index) && (index < humanRelationships.length) && humanRelationships[index][l]) {
                    tempSet.add(l);
                } else if ((l > index) && humanRelationships[l][index]) {
                    tempSet.add(l);
                }
            }
            // добавляем найденных друзей в результирующий сет
            resultSet.addAll(tempSet);
            // отталкиваясь от глубины запускаем цикл перебора друзей с 1й линии
            for (int i = 0; i < deep - 1; i++) {
                for (Integer temp : tempSet) {
                    for (int k = 0; k < humanRelationships.length; k++) {
                        if ((k < temp) && (temp < humanRelationships.length) && humanRelationships[temp][k]) {
                            tempSet2.add(k);
                        } else if ((k > temp) && humanRelationships[k][temp]) {
                            tempSet2.add(k);
                        }
                    }
                }
                // добавляем найденных в результирующий сет
                resultSet.addAll(tempSet2);
                // очищаем временный сет и заполняем его полученными друзьями для запуска следующего перебора.
                tempSet.clear();
                tempSet.addAll(tempSet2);
            }
        }
        // удаляем index из результирующего сета
        resultSet.remove(index);
        return resultSet;
    }

    // Remove from the set the people with whom you already have a relationship
    public Set<Integer> removeFriendsFromSet(Set<Integer> set, int index) {
        for (int i = 0; i < humanRelationships.length; i++) {
            if ((i < index) && (index < humanRelationships.length) && humanRelationships[index][i]) {
                set.remove(i);
            } else if ((i > index) && humanRelationships[i][index]) {
                set.remove(i);
            }
        }
        return set;
    }

    // Return test data
    private static boolean[][] generateRelationships() {
        return new boolean[][]{
                {true},                                                                 //0
                {true, true},                                                           //1
                {false, true, true},                                                    //2
                {false, false, false, true},                                            //3
                {true, true, false, true, true},                                        //4
                {true, false, true, false, false, true},                                //5
                {false, false, false, false, false, true, true},                        //6
                {false, false, false, true, false, false, false, true}                  //7
        };
    }
}