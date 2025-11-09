package day5;

import java.io.IOException;
import java.util.HashSet;

public class Day5_2 {
    public static void main(String[] args) throws IOException {
        var inputData = Day5_1.readInput();
        var rules = new HashSet<>(inputData.rules());
        int sum = inputData.sequences().stream()
                .filter(seq -> !Day5_1.check(seq, rules))
                .mapToInt(seq -> {
                    var sorted = seq.stream()
                            .sorted((a, b) -> rules.contains(new Rule(a, b)) ? -1 : 1)
                            .toList();
                    return sorted.get(sorted.size() / 2);
                })
                .sum();
        System.out.println(sum);
    }
}
