package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day7_1 {

    static Map<Long, List<Integer>> readInput() throws IOException {
        Map<Long, List<Integer>> data = new LinkedHashMap<>();
        for (var line : Files.readAllLines(Path.of("puzzleInputs", "day7.txt"))) {
            var parts = line.split(": ");
            long number = Long.parseLong(parts[0]);
            var nums = Arrays.stream(parts[1].split(" "))
                    .map(Integer::parseInt)
                    .toList();
            data.put(number, nums);
        }
        return data;
    }
    private static boolean check(long target, List<Integer> nums) {
        Queue<Long> queue = new LinkedList<>();
        queue.add((long) nums.getFirst());

        for (int j = 1; j < nums.size(); j++) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                long e = queue.poll();
                queue.add(e + nums.get(j));
                queue.add(e * nums.get(j));
            }
        }

        return queue.contains(target);
    }

    public static void main(String[] args) throws IOException {
        var data = readInput();
        long sum =  data.entrySet().stream()
                .filter(entry -> check(entry.getKey(), entry.getValue()))
                .mapToLong(Map.Entry::getKey)
                .sum();
        System.out.println(sum);
    }
}
