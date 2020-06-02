package com.javarush.task.task34.task3404;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Рекурсия для мат. выражения
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recurse("0+0.304", 0); //expected output 0.5 6

    }

    public void recurse(final String expression, int countOperation) {
// разобрать по скобкам
        String[] massive = expression.split("");
        int start = expression.lastIndexOf("(");
        String[] first = new String[0];
        String[] first2;
        int end = 0;
        if (start > 0) {
            end = expression.substring(start).indexOf(")") + start;
            first = expression.substring(start + 1, end).split(" ^.");
            first2 = first[0].split("");
        } else {
            first2 = massive;
        }

        if (first2[0].equals("")) first2[0] = expression;
//        System.out.println("first + " + Arrays.toString(first));
//        System.out.println("first2" + Arrays.toString(first2));
        // выполнить операции в первых скобках, посчитать кол-во операций
        double sum = 0;
        // на точку
        for (int i = 1; i < first2.length; i++) {
            if (first2[i].equals(".")) {
                first2[i - 1] = first2[i - 1] + first2[i] + first2[i + 1];
                first2[i] = "";
                first2[i + 1] = "";
            }
        }
        // переводим массив в List и удаляем пустые ячейки (можно потом сократить добаволением в лист всего и вызовом функции removeList)
        List<String> list = new ArrayList<>();
        for (int i = 0; i < first2.length; i++) {
            if (!first2[i].equals("")) {
                list.add(first2[i]);
            }
        }
//        System.out.println("after " + Arrays.toString(first2));
//        System.out.println("after list  " + list.toString());
        removeList(list);
        //нахождение унарного минуса
        for (int i = 0; i < list.size() - 1; i++) {
//            if (list.get(i).equals("-") && !list.get(i+1).matches("[a-z]*")){
            if (list.get(0).equals("-")) {
                list.set(i, "-" + list.get(i + 1));
                list.remove(i + 1);
                countOperation++;
            }
            if (list.get(i).matches("[*/^+-]?") && list.get(i + 1).equals("-")) {
                countOperation--;
                list.set(i + 2, "-" + list.get(i + 2));
                list.remove(i + 1);
                countOperation++;
            }
        }

//        System.out.println("after minus list  " + list.toString());
        concatList(list);
//        System.out.println("after concatList: " + list.toString());

        // отправить на проверку по функциям
        DecimalFormat df = new DecimalFormat("###.##");
        for (int i = 0; i < list.size(); i++) {
            double element = 0.0;
            String newElement;
            int st;
            if (list.get(i).contains("sin")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("sin")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
//                System.out.println("длина строки " + list.get(i).length());
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = -1 * Math.sin(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(3));
                    element = Math.sin(Math.toRadians(Double.parseDouble(list.get(i).substring(3))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
            if (list.get(i).contains("cos")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("cos")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = -1 * Math.cos(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(3));
                    element = Math.cos(Math.toRadians(Double.parseDouble(list.get(i).substring(3))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
            if (list.get(i).contains("tan")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("tan")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
//                System.out.println("длина строки " + list.get(i).length());
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = -1 * Math.tan(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(3));
                    element = Math.tan(Math.toRadians(Double.parseDouble(list.get(i).substring(3))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
            if (list.get(i).contains("asin")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("asin")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(5));
                    element = -1 * Math.asin(Math.toRadians(Double.parseDouble(list.get(i).substring(5))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = Math.asin(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
            if (list.get(i).contains("acos")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("acos")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(5));
                    element = -1 * Math.acos(Math.toRadians(Double.parseDouble(list.get(i).substring(5))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = Math.acos(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
            if (list.get(i).contains("atan")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("atan")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(5));
                    element = -1 * Math.atan(Math.toRadians(Double.parseDouble(list.get(i).substring(5))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = Math.atan(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
            if (list.get(i).contains("exp")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("exp")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = -1 * Math.exp(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(3));
                    element = Math.exp(Math.toRadians(Double.parseDouble(list.get(i).substring(3))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
            if (list.get(i).contains("log")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("log")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = -1 * Math.log(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(3));
                    element = Math.log(Math.toRadians(Double.parseDouble(list.get(i).substring(3))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
            if (list.get(i).contains("abs")) {
                // делаем проверку на отрицательное число функции
                if (list.get(i).endsWith("abs")) {
                    list.set(i, list.get(i) + list.get(i + 1) + list.get(i + 2));
                    list.set(i + 1, "");
                    list.set(i + 2, "");
                }
                if (list.get(i).startsWith("-")) {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(4));
                    element = -1 * Math.abs(Math.toRadians(Double.parseDouble(list.get(i).substring(4))));
                } else {
//                    System.out.println("list.get(i).substring(st)" + list.get(i).substring(3));
                    element = Math.abs(Math.toRadians(Double.parseDouble(list.get(i).substring(3))));
                }
//                System.out.println("ELEMENT" + element);
                newElement = df.format(element);
                newElement = newElement.replaceAll(",", ".");
                list.set(i, newElement);
                countOperation++;
            }
        }
//        System.out.println("after sin " + list.toString() + " " + countOperation);
        // на степень, т.к. она идет в приоритете. Если делать общую проверку с */ то приходилось запускать обратный цикл,
        // в результате операции умножения и деления в обратном порядке дают неверный результат
        for (int i = list.size() - 1; i > 0; i--) {
            if (list.get(i).equals("^")) {
                sum = Math.pow(Double.parseDouble(list.get(i - 1)), Double.parseDouble(list.get(i + 1)));
                countOperation++;
                list.set(i - 1, String.valueOf(sum));
                list.set(i + 1, "");
                list.set(i, "");
            }
        }
        removeList(list);

        // на умножение, деление
        for (int i = 1; i < list.size() - 1; i++) {
            if (list.get(i).equals("/")) {
                sum = Double.parseDouble(list.get(i - 1)) / Double.parseDouble(list.get(i + 1));
                countOperation++;
                list.set(i + 1, String.valueOf(sum));
                list.set(i - 1, "");
                list.set(i, "");
            } else if (list.get(i).equals("*")) {
                sum = Double.parseDouble(list.get(i - 1)) * Double.parseDouble(list.get(i + 1));
                countOperation++;
                list.set(i + 1, String.valueOf(sum));
                list.set(i - 1, "");
                list.set(i, "");
            }
        }
//        System.out.println("after /* list  " + list.toString());
        removeList(list);
//        System.out.println("after removeList  " + list.toString());
        // на сложение вычитание
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).equals("+")) {
                sum = Double.parseDouble(list.get(i - 1)) + Double.parseDouble(list.get(i + 1));
                countOperation++;
                list.set(i + 1, String.valueOf(sum));
                list.set(i - 1, "");
                list.set(i, "");
            } else if (list.get(i).equals("-")) {
                sum = Double.parseDouble(list.get(i - 1)) - Double.parseDouble(list.get(i + 1));
                countOperation++;
                list.set(i + 1, String.valueOf(sum));
                list.set(i - 1, "");
                list.set(i, "");
            }
        }
        removeList(list);
//        System.out.println("after -+ removeList  " + list.toString());
        concatList(list);
// создаем новый expression для передачи рекурсией
        String newExpression;

        try {
            String expr = expression.substring(0, start);
            String expr2 = expression.substring(end + 1);
            newExpression = expr.concat(list.get(0)).concat(expr2);
//            System.out.println("newExpression: " + newExpression);
            // удаляем первую и последнюю скобку, в случае если у нас пример имеет вид: (-1 + (-2))
            int firstBraces = newExpression.indexOf("(");
            int lastBraces = newExpression.lastIndexOf(")");
            if (firstBraces == 0 && lastBraces == newExpression.length() - 1) {
                newExpression = newExpression.substring(firstBraces + 1, lastBraces);
//                System.out.println("After braces " + newExpression);
            }
            if (newExpression.contains(")") && !newExpression.contains("(")) {
                System.out.println(String.format("%s %d", df.format(Double.parseDouble(list.get(0))),countOperation).replace(",","."));
                return;
            }
            recurse(newExpression, countOperation);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(String.format("%s %d", df.format(Double.parseDouble(list.get(0))),countOperation).replace(",","."));
            return;
        }
    }
    public List<String> removeList(List<String> list) {
        list.removeIf(n -> (n == ""));
        list.removeIf(n -> (n.matches("[ ]*")));
        list.removeIf(n -> (n.matches("[()]*")));
        return list;
    }
    public List<String> concatList(List<String> list) {
        String temp;
        int count = 0;
        for (int i = list.size() - 1; i > 0; i--) {
            if (!list.get(i - 1).matches("[*/^+-]?") && !list.get(i).matches("[*/^+-]?")) {
                temp = list.get(i - 1) + list.get(i);
                list.set(i - 1, temp);
                list.set(i, "");
            }
        }
        removeList(list);

        for (int i = list.size() - 1; i > 0; i--) {
            if (list.get(i - 1).equals("*") || list.get(i - 1).equals("/") || list.get(i - 1).equals("-") || list.get(i - 1).equals("+") || list.get(i - 1).equals("^")) {
//                System.out.println("-------------------------------------------------------->");
            } else {
                if (!list.get(i).equals("*") && !list.get(i).equals("/") && !list.get(i).equals("-") && !list.get(i).equals("+") && !list.get(i).equals("^")) {
                    if (list.get(i - 1).contains("-") || list.get(i).contains("-")) {
                        if ((i - 2) > 0 | (i + 2) < list.size()) {
                            double d;
                            try {
                                if (!list.get(i - 2).matches("[*/]*") && !list.get(i + 1).matches("[*/]*")) {
                                    d = Double.parseDouble(list.get(i - 1)) + Double.parseDouble(list.get(i));
                                    list.set(i - 1, String.valueOf(d));
                                    list.set(i, "");
                                    i--;
                                    count++;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                d = Double.parseDouble(list.get(i - 1)) + Double.parseDouble(list.get(i));
                                list.set(i - 1, String.valueOf(d));
                                list.set(i, "");
                                i--;
                                count++;
                            }
                        }
                    } else {

                        temp = list.get(i - 1) + list.get(i);
                        list.set(i - 1, temp);
                        list.set(i, "");
                        i--;
                        count++;

                    }
                }
            }
        }
        if (count > 0) {
            removeList(list);
            count = 0;
        }
        if (count > 0) {
            removeList(list);
            concatList(list);
        }
        return list;
    }

    public Solution() {
        //don't delete
    }
}
