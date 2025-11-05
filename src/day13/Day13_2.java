package day13;

import java.io.IOException;
import java.util.List;

import static day13.Day13_1.*;

public class Day13_2 {

    static void prepareData(List<long[]> data){
        data.forEach(cs -> {
            cs[4] += 10_000_000_000_000L;
            cs[5] += 10_000_000_000_000L;
        });
    }

    public static void main(String[] args) throws IOException {
        var data = readInput();
        prepareData(data);
        long sum = data.stream().mapToLong(Day13_1::solveOne).sum();
        System.out.println(sum);
    }
}
