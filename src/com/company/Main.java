package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class MappingSets{
    String stringN;
    String stringM;
    /**
     * countOfRetainSets - пересечение между символами срок
     */
    int countOfRetainSets;

    public MappingSets(String stringN, String stringM) {
        this.stringN = stringN;
        this.stringM = stringM;
        List<Character> uniqCharN = stringN.chars().mapToObj(c->(char) c).collect(Collectors.toList());
        List<Character> uniqCharM = stringM.chars().mapToObj(c->(char) c).collect(Collectors.toList());
        countOfRetainSets=returnUniqueCharsInLists(uniqCharM, uniqCharN);
    }

    int returnUniqueCharsInLists(List<Character> list1, List<Character> list2) {
        List<Character> resultSet = new ArrayList<>(list1);
        resultSet.retainAll(list2);
        return resultSet.size();
    }

}

public class Main {


    public static void main(String[] args) throws IOException {

        Set<String> setN = new LinkedHashSet<>();
        Set<String> setM = new LinkedHashSet<>();

        Map<String, String> resultMap = new HashMap<>();

        ArrayList<MappingSets> mappingSetsArrayList = new ArrayList<>();

        FileReader fileReader = new FileReader("input.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int n = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < n; i++) {
            setN.add(bufferedReader.readLine());
        }

        int m = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < m; i++) {
            String line = bufferedReader.readLine();
            setM.add(line);

            /**
             * Перебираем все возможные варианты и считаем сколько символов пересекаются между строками при помощи конструктора класса MappingSets
             */
            setN.forEach(s -> {
                MappingSets mappingSets = new MappingSets(s, line);
                mappingSetsArrayList.add(mappingSets);
            });
        }

        /**
         * Сортируем Список в обратном порядке по количеству повторяющих символов
         */
        mappingSetsArrayList.sort(Comparator.comparingInt(x -> x.countOfRetainSets));
        Collections.reverse(mappingSetsArrayList);

        /**
         * Алгоритм такой:
         * В отсортированном списке мы перебираем значения
         * repeatingWords - в эту переменную записываются слова, которые были уже использованы из M множества
         * Сначала проверяем, есть ли значение в мапе результата resultMap.
         * Если нет - то проверяем, использовалось ли слово из M множества.
         * Если нет, то добавляем новый результат в мапу результата и в множество повторяющих слов
         *
         * Если значение ключ есть - то смотрим, если значение равно "?", значит он попадался уже с использованными строками.
         * (Так как список отсортированный в обратном порядке - то все значения без "?" имеют уже лучший результат.)
         *
         * Ну и также проверяем, если слово не использовалось - то используем его
         *
         *
         */

        Map<String, Integer> repeatingWords = new HashMap<>();
        mappingSetsArrayList.forEach(mappingSets -> {
            if (resultMap.get(mappingSets.stringN)==null) {
                if (!repeatingWords.containsKey(mappingSets.stringM)){
                    repeatingWords.put(mappingSets.stringM, mappingSets.countOfRetainSets);
                    resultMap.put(mappingSets.stringN, mappingSets.stringM);
                } else resultMap.put(mappingSets.stringN, "?");
            } else if (resultMap.get(mappingSets.stringN).equals("?"))
                if (!repeatingWords.containsKey(mappingSets.stringM)) {
                    repeatingWords.put(mappingSets.stringM, mappingSets.countOfRetainSets);
                    resultMap.put(mappingSets.stringN, mappingSets.stringM);
                }

        });


        /**
         * находим разницу между двумя множествами, чтобы не потерять не обработанные строки из множества M
         * И добавляем их в мапу resultMap
         */
        setM.removeAll(repeatingWords.keySet());
        if (!setM.isEmpty()){
            setN.addAll(setM);
            setM.forEach(s -> resultMap.put(s,"?"));
        }

        FileWriter fileWriter = new FileWriter("output.txt");
        fileWriter.flush();
        setN.forEach(s -> {
            try {
                fileWriter.write(new StringBuilder(s).append(":").append(resultMap.get(s)).append("\n").toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileWriter.close();

    }



}
