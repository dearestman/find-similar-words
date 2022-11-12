package com.company.gitignore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main2 {


    public static void main(String[] args) throws IOException {


        Map<String, List<Character>> mapN = new LinkedHashMap<>();
        Map<String, List<Character>> mapM = new LinkedHashMap<>();

        Map<String, String> resultMap = new HashMap<>();


        FileReader fileReader = new FileReader("input.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        readFile(mapN, bufferedReader);
        readFile(mapM, bufferedReader);


        for (Map.Entry<String, List<Character>> entryM : mapM.entrySet()) {
            int max = -1;
            StringBuilder solutionString = new StringBuilder();
            for (Map.Entry<String, List<Character>> entryN : mapN.entrySet()) {
                int uniqueCharsInSets = returnUniqueCharsInLists(entryM.getValue(), entryN.getValue());
                if (uniqueCharsInSets > max ) {
                    max = uniqueCharsInSets;
                    solutionString = new StringBuilder(entryN.getKey());
                }
            }
            if (resultMap.get(solutionString.toString())!=null) {
                int uniqueCharsInResultSet = returnUniqueCharsInLists(mapN.get(solutionString.toString()), mapM.get(resultMap.get(solutionString.toString())));
                if (uniqueCharsInResultSet<max)
                    resultMap.put(String.valueOf(solutionString), entryM.getKey());
            } else {
                resultMap.put(String.valueOf(solutionString), entryM.getKey());
            }

        }

        mapM.keySet().removeAll(resultMap.values());
        mapN.putAll(mapM);

        FileWriter fileWriter = new FileWriter("output.txt");
        fileWriter.flush();
        mapN.forEach((key, value) -> {
            try {
                fileWriter.write(resultMap.get(key)==null
                        ? new StringBuilder(key).append(":?").append("\n").toString()
                        : new StringBuilder(key).append(":").append(resultMap.get(key)).append("\n").toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileWriter.close();

    }

    private static void readFile(Map<String, List<Character>> mapN, BufferedReader bufferedReader) throws IOException {
        int n = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < n; i++) {
            String line = bufferedReader.readLine();
            mapN.put(line, line.chars().mapToObj(c->(char) c).collect(Collectors.toList()));
        }
    }


    private static int returnUniqueCharsInLists(List<Character> set1, List<Character> set2) {
        List<Character> resultSet = new ArrayList<>(set1);
        resultSet.retainAll(set2);
        return resultSet.size();
    }
}
