package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int a;
    static int b;
    static int index;
    static String mathSymb;
    static String res="";
    static boolean rightInput = false;

    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(calc(input.readLine().toUpperCase().replace(" ", "")));
        input.close();

    }

    public static String calc(String input) {
        TreeMap<Integer, String> romanNums = new TreeMap<>(Collections.reverseOrder());
        romanNums.put(1, "I");
        romanNums.put(2, "II");
        romanNums.put(3, "III");
        romanNums.put(4, "IV");
        romanNums.put(5, "V");
        romanNums.put(6, "VI");
        romanNums.put(7, "VII");
        romanNums.put(8, "VIII");
        romanNums.put(9, "IX");
        romanNums.put(10, "X");

        try {
            String mathSymbols = "+-*/";
            int match = 0;
            for (int i = 0; i < mathSymbols.length(); i++) {
                for (int j = 0; j < input.length(); j++) {
                    if (mathSymbols.charAt(i) == input.charAt(j)) {
                        mathSymb= String.valueOf(mathSymbols.charAt(i));//Присваивание переменной данного мат символа операции
                        match++;
                    }
                }
            }
            if (match <1) {
                throw new PrivateException("Cтрока не является математической операцией");
            }else if (match >1) {
                throw new PrivateException("Формат математической операции не удовлетворяет заданию");
            }else{
                index = input.indexOf(mathSymb);//Поиск позиции математического символа операции в строке
            }
              String firstDigitStr = input.substring(0, index); //Присваивание первого значения до знака переменной
            String secondDigitStr = input.substring(++index);//Присваивание второго значения после знака переменной
            if ((((byte) firstDigitStr.charAt(0) > 47) && ((byte) firstDigitStr.charAt(0) < 58)) && (((byte) secondDigitStr.charAt(0) > 47) && ((byte) secondDigitStr.charAt(0) < 58))) {//если числа попадают в интервал от 0 до 9
                if ((romanNums.containsKey(Integer.parseInt(firstDigitStr)) && romanNums.containsKey(Integer.parseInt(secondDigitStr)))) {//если числа содержатся в romanNums
                    res = getArabicalRes(Integer.parseInt(firstDigitStr), Integer.parseInt(secondDigitStr));
                    rightInput = true;
                } else throw new PrivateException("Введеные арабские числа больше 10,меньше 1 или не целые");
            }
            if (((((byte) firstDigitStr.charAt(0) == 73) | ((byte) firstDigitStr.charAt(0) == 86) | ((byte) firstDigitStr.charAt(0) == 88)) &
                    (((byte) secondDigitStr.charAt(0) == 73) | ((byte) secondDigitStr.charAt(0) == 86) | ((byte) secondDigitStr.charAt(0) == 88)))) {//Проверка если оба числа римские
                if (romanNums.containsValue(firstDigitStr) && romanNums.containsValue(secondDigitStr)) {
                    res = getRomRes(romanNums, firstDigitStr, secondDigitStr);
                    rightInput = true;
                } else throw new PrivateException("Введены неподходящие римские цифры");
            }

            if (!rightInput) {
                throw new PrivateException("Используются одновременно цифры разных написаний");
            }

        } catch (PrivateException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Введены несоответствующие арабские числа");
            System.exit(1);
        }
        return res;
    }

    public static String getArabicalRes(Integer a, Integer b) {

        if (mathSymb.equals("+")) {
            res = String.valueOf(sum(a, b));
        }
        if (mathSymb.equals("-")) {
            res = String.valueOf(substraction(a, b));
        }
        if (mathSymb.equals("*")) {
            res = String.valueOf(multiplication(a, b));
        }
        if (mathSymb.equals("/")) {
            res = String.valueOf(division(a, b));
        }
        return res;
    }

    public static String getRomRes(TreeMap<Integer, String> romanNums, String firstDigitStr, String
            secondDigitStr) throws PrivateException {
        int romRes = 0;
        for (Map.Entry<Integer, String> pair : romanNums.entrySet()) {
            if (firstDigitStr.equals(pair.getValue())) {
                a = pair.getKey();
            }
        }
        for (Map.Entry<Integer, String> pair : romanNums.entrySet()) {
            if (secondDigitStr.equals((pair.getValue()))) {
                b = pair.getKey();
            }
        }
        switch (mathSymb) {
            case "+":
                romRes = sum(a, b);
                break;
            case "-":
                romRes = substractionRom(a, b);
                break;
            case "*":
                romRes = multiplication(a, b);
                break;
            case "/":
                romRes = divisionRom(a, b);
                break;

        }
        if (romRes < 0) {
            throw new PrivateException("Результатом работы калькулятора с римскими числами могут быть только положительные числа");
        } else {
            addTreeMap(romanNums);

            while (romRes > 0) {//пока полученное число больше нуля
                for (Map.Entry<Integer, String> pair : romanNums.entrySet()) {//Перебор сортированной мапы по убыванию
                    while (romRes >= pair.getKey()) {// Как только значение совпадет максимально возможному значению из пары ThreeMap
                        res += pair.getValue();//присвоить переменной буквенное значение из пары ThreeMap
                        romRes -= pair.getKey();//Вычесть полученное максимальное значение пары TreeMap из нашего значения, чтобы перейти к меньшему разряду
                    }
                }
            }

        }

        return res;
    }

    public static int sum(int a, int b) {
        return a + b;
    }

    public static int substraction(int a, int b) {
        return a - b;
    }

    public static int multiplication(int a, int b) {
        return a * b;
    }

    public static int division(int a, int b) {
        return a / b;
    }


    public static int substractionRom(int a, int b) {
        if ((a - b) < 1) {
            try {
                throw new Exception();

            } catch (Exception e) {
                System.out.println("Результат вычитания не может быть меньше единицы");
                System.exit(1);
            }
        }
        return a - b;
    }

    public static int divisionRom(int a, int b) {
        if ((a / b) < 1) {
            try {
                throw new Exception();

            } catch (Exception e) {
                System.out.println("Результат деления не может быть меньше единицы");
                System.exit(1);
            }

        }
        return a / b;
    }

    public static TreeMap<Integer, String> addTreeMap(TreeMap<Integer, String> romanNums) {
        romanNums.put(40, "XL");
        romanNums.put(50, "L");
        romanNums.put(90, "XL");
        romanNums.put(100, "C");
        return romanNums;
    }
}

class PrivateException extends Exception {

    public PrivateException(String message) {
        super(message);
    }
}