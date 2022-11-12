package com.company.gitignore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main4 {


    public static void main(String[] args) throws IOException {

        Set<String> inputStringsN = new LinkedHashSet<>();
        Set<String> inputStringsM = new LinkedHashSet<>();

        Map<String, List<Character>> mapN = new HashMap<>();
        Map<String, List<Character>> mapM = new HashMap<>();

        Map<String, String> resultMap = new HashMap<>();


        FileReader fileReader = new FileReader("input.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        readFile(inputStringsN, mapN, bufferedReader);
        readFile(inputStringsM, mapM, bufferedReader);



        for (Map.Entry<String, List<Character>> entryM : mapM.entrySet()) {
                int max = -1;
                StringBuilder solutionString = new StringBuilder();
                for (Map.Entry<String, List<Character>> entryN : mapN.entrySet()) {
                    int uniqueCharsInSets = returnUniqueCharsInLists(entryM.getValue(), entryN.getValue());
                    if (uniqueCharsInSets > max) {
                        max = uniqueCharsInSets;
                        solutionString = new StringBuilder(entryN.getKey());
                    }
                }
                resultMap.put(String.valueOf(solutionString), entryM.getKey());
            }

        inputStringsM.removeAll(resultMap.values());
        if (!inputStringsM.isEmpty()){
            inputStringsN.addAll(inputStringsM);
        }


        FileWriter fileWriter = new FileWriter("output.txt");
        fileWriter.flush();
        inputStringsN.stream()
                .map(s -> resultMap.get(s) == null
                        ? new StringBuilder(s).append(":?")
                        : new StringBuilder(s).append(":").append(resultMap.get(s)))
                .forEach(s -> {
            try {
                fileWriter.write( s.append(" \n").toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileWriter.close();

    }

    private static void readFile(Set<String> inputStringsN, Map<String, List<Character>> mapN, BufferedReader bufferedReader) throws IOException {
        int n = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < n; i++) {
            String line = bufferedReader.readLine();
            mapN.put(line, line.chars().mapToObj(c->(char) c).collect(Collectors.toList()));
            inputStringsN.add(line.toString());
        }
    }


    private static int returnUniqueCharsInLists(List<Character> set1, List<Character> set2) {
        List<Character> resultSet = new ArrayList<>(set1);
        resultSet.retainAll(set2);
        return resultSet.size();
    }
}
