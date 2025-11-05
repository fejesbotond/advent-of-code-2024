package day11;

import java.util.HashMap;

public class Day11_1 {

    static void solve(int iterations){
        var current = new HashMap<Long, Long>();

        long[] keys = {2L, 77706L, 5847L, 9258441L, 0L, 741L, 883933L, 12L};

        for (long key : keys) {
            current.put(key, 1L);
        }
        for(int i = 0; i < iterations; i++){
            var next = new HashMap<Long, Long>();
            for(var entry : current.entrySet()){
                var k = entry.getKey();
                var c = entry.getValue();
                if(k == 0L){
                    next.merge(1L, c, Long::sum);
                }else{
                    var s = Long.toString(k);
                    if(s.length() % 2 == 0){
                        var left = Long.parseLong(s.substring(0, s.length()/2));
                        var right = Long.parseLong(s.substring(s.length()/2));
                        next.merge(left, c, Long::sum);
                        next.merge(right, c, Long::sum);
                    }else{
                        next.merge(2024L * k, c, Long::sum);
                    }
                }
            }
            current = next;
        }
        var sum = current.values().stream().mapToLong(l->l).sum();
        System.out.println(sum);
    }
    public static void main(String[] args){
        solve(25);
    }
}
