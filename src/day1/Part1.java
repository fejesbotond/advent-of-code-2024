package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class Part1 {

    static List<int[]> readInput() throws IOException {
        try (var lines = Files.lines(Path.of("puzzleInputs", "day1.txt"))) {
            return lines
                    .map(line -> Arrays.stream(line.split("\\s+"))
                            .mapToInt(Integer::parseInt)
                            .toArray()
                    )
                    .toList();
        }
    }

    public static void main(String[] args) throws IOException {
        var pairs = readInput();

        BiFunction<List<int[]>, Integer, int[]> pipeline = (prs, i) ->
                prs.stream()
                        .mapToInt(pair -> pair[i])
                        .sorted()
                        .toArray();

        var arr1 = pipeline.apply(pairs, 0);
        var arr2 = pipeline.apply(pairs, 1);

        var result = IntStream.range(0, arr1.length).map(i -> Math.abs(arr1[i] - arr2[i])).sum();

        System.out.println(result);

    }
}
