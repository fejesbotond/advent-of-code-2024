package day1;

import java.io.IOException;

public class Part2 {
    public static void main(String[] args) throws IOException {
        var pairs = Part1.readInput();

        var left = pairs.stream().mapToInt(p -> p[0]).toArray();
        var right = pairs.stream().mapToInt(p -> p[1]).toArray();

        var similarityScore = 0;
        for (var x : left) {
            var count = 0;
            for (var y : right) {
                if (x == y) {
                    count++;
                }
            }
            similarityScore += count * x;
        }
        System.out.println(similarityScore);
    }
}
