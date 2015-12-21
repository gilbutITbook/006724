package ch08.sec04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtractingCombining {
    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print(title + ": ");
        if (firstElements.size() <= SIZE)
            System.out.println(firstElements);
        else {
            firstElements.remove(SIZE);
            String out = firstElements.toString();
            System.out.println(out.substring(0, out.length() - 1) + ", ...]");
        }
    }

    public static Stream<String> letters(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++)
            result.add(s.substring(i, i + 1));
        return result.stream();
    }

    public static void main(String[] args) throws IOException {
        Stream<Double> randoms = Stream.generate(Math::random).limit(100);
        show("randoms", randoms);

        Stream<Integer> firstFive = Stream.iterate(0, n -> n + 1).limit(5);
        show("firstFive", firstFive);

        Stream<Integer> notTheFirst = Stream.iterate(0, n -> n + 1).skip(1);
        show("notTheFirst", notTheFirst);

        Stream<String> combined = Stream.concat(letters("Hello"),
                letters("World"));
        show("combined", combined);
    }
}