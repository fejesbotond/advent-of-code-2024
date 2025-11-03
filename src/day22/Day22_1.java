package day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day22_1 {

    private static void solve() throws IOException{
        List<Integer> data = readInput();
        long sum = 0L;
        for(Integer num : data){
            long s = num;
            for(int i = 0; i < 2000; i++){
                s = step(s);
            }
            sum += s;
        }
        System.out.println(sum);

    }

   static long step(long num){
        num = ((num << 6) ^ num) & 0xffffffL;
        num = ((num >> 5) ^ num) & 0xffffffL;
        num = ((num << 11) ^ num) & 0xffffffL;
        return num;
    }
    static List<Integer> readInput() throws IOException {
        try (var lines = Files.lines(Path.of("puzzleInputs","day22.txt"))) {
            return lines.map(Integer::parseInt).toList();
        }
    }

    public static void main(String[] args) throws IOException {
        solve();


    }
}
