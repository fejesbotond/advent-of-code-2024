package day7;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class Day7_2 {

    private static boolean check(long target, List<Integer> nums) {
        Queue<Long> queue = new LinkedList<>();
        queue.add((long) nums.getFirst());

        for (int j = 1; j < nums.size(); j++) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                long e = queue.poll();
                queue.add(e + nums.get(j));
                queue.add(e * nums.get(j));
                queue.add(Long.parseLong("" + e + nums.get(j)));
            }
        }

        return queue.contains(target);
    }
    public static void main(String[] args) throws IOException {
        var data = Day7_1.readInput();
        long sum =  data.entrySet().stream()
                .filter(entry -> check(entry.getKey(), entry.getValue()))
                .mapToLong(Map.Entry::getKey)
                .sum();
        System.out.println(sum);
    }
}
