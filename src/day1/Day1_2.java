package day1;

import java.io.IOException;
import java.util.stream.Collectors;

public class Day1_2 {
    public static void main(String[] args) throws IOException {
        var pairs = Day1_1.readInput();

        var left = pairs.stream().map(p -> p[0]).toList();
        var right = pairs.stream().map(p -> p[1]).toList();

        var rightCounts = right.stream()
                .collect(Collectors.groupingBy(y -> y, Collectors.counting()));

        var similarityScore = left.stream()
                .mapToLong(x -> x * rightCounts.getOrDefault(x, 0L))
                .sum();

        System.out.println(similarityScore);
    }
}
