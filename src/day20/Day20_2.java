package day20;

import java.io.IOException;
import java.util.List;

public class Day20_2 {

    private static void solve() throws IOException{
        char[][] grid = Day20_1.readInput();
        List<IntTuple> path = Day20_1.path(grid);

        int count = 0;
        for(int i = 0; i < path.size() - 1; i++){
            for(int j = i + 1; j < path.size(); j++){
                var n1 = path.get(i);
                var n2 = path.get(j);
                int distance = Math.abs(n1.x() - n2.x()) + Math.abs(n1.y() - n2.y());
                if(distance <= 20){
                    int timeSaved = Math.abs(i - j) - distance;
                    if(timeSaved >= 100){
                        count++;
                    }
                }

            }
        }
        System.out.println(count);
    }
    public static void main(String[] args) throws IOException{
        System.out.println("Day 20 - part 2");
        long s = System.nanoTime();
        solve();
        long e = System.nanoTime();
        double seconds = (e - s) / 1_000_000_000.0;
        String f = String.format("%.3f", seconds);
        System.out.println(f + " sec");
    }
}
