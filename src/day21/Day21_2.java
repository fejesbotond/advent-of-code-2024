package day21;

import java.io.IOException;
import java.util.Map;

public class Day21_2 {

    private static Map<Move, Long> computeCacheLevel25(Map<Move, Long> cacheLevel1){
        var curr = cacheLevel1;
        for(int i = 0; i < 24; i++){
            curr = Day21_1.computeCacheLevel2(curr);
        }
        return curr;
    }
    public static void main(String[] args) throws IOException{
        var cacheLevel1 = Day21_1.computeCacheLevel1();
        var cacheLevel25 = computeCacheLevel25(cacheLevel1);

        Day21_1.solve(cacheLevel25);

    }
}
