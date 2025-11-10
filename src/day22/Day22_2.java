package day22;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Day22_2 {

    private static class IntWindow4{
        private final int[] buffer = new int[4];
        private int head = 0;

        void add(int e){
            buffer[head] = e;
            head = (head + 1) & 3;
        }
        int encode(){
            int array4 = 0;
            for(int i = 0; i < 4; i++){
               int it = buffer[(head + i) & 3];
               int u = it + 9;              // [-9, 9] => [0, 18]
               array4 = (array4 << 8) | u;  //let's code into 8-8-8-8 bits

            }
            return array4;
        }
    }

    private static void solve() throws IOException {
        List<Integer> data = Day22_1.readInput();
        var table = new ConcurrentHashMap<Integer, Integer>();

        data.parallelStream().forEach(s -> {
            long curr = s;
            var localTable = new HashSet<Integer>();
            var window = new IntWindow4();
            for (int i = 0; i < 2000; i++) {
                long next = Day22_1.step(curr);
                int diff = (int)(next % 10 - curr % 10);
                curr = next;
                window.add(diff);

                if (i >= 3) {
                    int key = window.encode();
                    if (localTable.add(key)) {
                        table.merge(key, (int) (next % 10), Integer::sum);
                    }
                }
            }
        });

        int max = table.values().stream().mapToInt(i -> i).max().orElseThrow();
        System.out.println(max);
    }

    public static void main(String[] args) throws IOException{
        long s = System.nanoTime();
        solve();
        long e = System.nanoTime();
        double seconds = (e - s) / 1_000_000_000.0;
        String f = String.format("%.3f", seconds);
        System.out.println(f + " sec");
    }
}
