package ch08.sec14;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

public class ParallelStreams {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(
                Paths.get("alice.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        // Very bad code ahead
        int[] shortWords = new int[10];
        words.parallelStream().forEach(
            s -> { if (s.length() < 10) shortWords[s.length()]++; });
        System.out.println(Arrays.toString(shortWords));

        // Try again--the result will likely be different (and also wrong)
        Arrays.fill(shortWords, 0);
        words.parallelStream().forEach(
            s -> { if (s.length() < 10) shortWords[s.length()]++; });
        System.out.println(Arrays.toString(shortWords));

        // Remedy: Group and count 
        Map<Integer, Long> shortWordCounts =
            words.parallelStream()
                .filter(s -> s.length() < 10)
                .collect(
                    groupingBy(
                        String::length,
                        counting()));
               
        System.out.println(shortWordCounts);
        
        // Downstream order not deterministic
        Map<Integer, List<String>> result = words.parallelStream().collect(
                Collectors.groupingByConcurrent(String::length));

        System.out.println(result.get(14));

        result = words.parallelStream().collect(
                Collectors.groupingByConcurrent(String::length));

        System.out.println(result.get(14));
        
        Map<Integer, Long> wordCounts =
                words.parallelStream()
                    .collect(
                        groupingByConcurrent(
                            String::length,
                            counting()));
                   
        System.out.println(wordCounts);
   }
}
