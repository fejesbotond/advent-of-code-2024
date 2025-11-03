package day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;

public class Day21_1 {

    private static final Map<Dir, Pos> dirToPos = Map.of(
            Dir.Up, new Pos(0, 1),
            Dir.Down, new Pos(1, 1),
            Dir.Left, new Pos(1, 0),
            Dir.Right, new Pos(1, 2),
            Dir.A, new Pos(0, 2)
    );

    private static final Map<Integer, Pos> numericToPos = Map.ofEntries(
            Map.entry(0, new Pos(3, 1)),
            Map.entry(1, new Pos(2, 0)),
            Map.entry(2, new Pos(2, 1)),
            Map.entry(3, new Pos(2, 2)),
            Map.entry(4, new Pos(1, 0)),
            Map.entry(5, new Pos(1, 1)),
            Map.entry(6, new Pos(1, 2)),
            Map.entry(7, new Pos(0, 0)),
            Map.entry(8, new Pos(0, 1)),
            Map.entry(9, new Pos(0, 2)),
            Map.entry(10, new Pos(3, 2))
    );

    static String[] readInput() throws IOException{
        return Files.readAllLines(Path.of("puzzleInputs", "day21.txt")).toArray(new String[0]);
    }
    static Map<Move, Long> computeCacheLevel1(){
        var cache = new HashMap<Move, Long>();
        for(var b1 : Dir.values()){
            for(var b2 : Dir.values()){
                var p1 = dirToPos.get(b1);
                var p2 = dirToPos.get(b2);
                var dx = Math.abs(p1.x() - p2.x());
                var dy = Math.abs(p1.y() - p2.y());
                cache.put(new Move(b1, b2), dx + dy + 1L);
            }
        }
        return Collections.unmodifiableMap(cache);
    }

    private static <E> List<Dir[]> paths(E from, E to){
        Pos startPos, endPos;
        int gapX, gapY;
        if(from instanceof Dir && to instanceof Dir){
            startPos = dirToPos.get(from);
            endPos = dirToPos.get(to);
            gapX = 0;
            gapY = 0;
        }else if( from instanceof Integer && to instanceof Integer){
            startPos = numericToPos.get(from);
            endPos = numericToPos.get(to);
            gapX = 3;
            gapY = 0;
        } else throw new IllegalArgumentException();

        var dx = endPos.x() - startPos.x();
        var dy = endPos.y() - startPos.y();
        var xDir = dx < 0 ? Dir.Up : Dir.Down;
        var yDir = dy < 0 ? Dir.Left : Dir.Right;
        dx = Math.abs(dx);
        dy = Math.abs(dy);

        var choices = new boolean[dx + dy];
        var collector = new ArrayList<boolean[]>();
        collect(dx, dy, choices, 0, collector);

        return collector.stream()
                .map(arr ->
                    IntStream.range(0, arr.length)
                            .mapToObj(i -> arr[i] ? yDir : xDir)
                            .toArray(Dir[]::new)
                )
                .filter(arr -> checkPath(startPos, gapX, gapY, arr))
                .toList();

    }

    private static boolean checkPath(Pos start, int targetX, int targetY, Dir[] path) {
        int currX = start.x();
        int currY = start.y();

        for (Dir dir : path) {
            if (currX == targetX && currY == targetY) {
                return false;
            }
            switch (dir) {
                case Up    -> currX--;
                case Down  -> currX++;
                case Left  -> currY--;
                case Right -> currY++;
            }
        }
        return true;
    }

    private static void collect(int dx, int dy, boolean[] choices, int index, List<boolean[]> collector) {
        if (dx == 0 || dy == 0) {
            boolean value = dx == 0;
            int count = Math.max(dx, dy);

            for (int i = 0; i < count; i++) {
                choices[choices.length - i - 1] = value;
            }

            collector.add(Arrays.copyOf(choices, choices.length));
            return;
        }

        choices[index] = false;
        collect(dx - 1, dy, choices, index + 1, collector);

        choices[index] = true;
        collect(dx, dy - 1, choices, index + 1, collector);
    }

    static Map<Move, Long> computeCacheLevel2(Map<Move, Long> cacheLevel1){
        var cache = new HashMap<Move, Long>();
        for(var b1 : Dir.values()){
            for(var b2 : Dir.values()){
                if(b1.equals(b2)){
                    cache.put(new Move(b1, b2), 1L);

                }
                else{
                    var paths = paths(b1, b2);
                    var bestValue = paths.stream()
                            .mapToLong(arr -> getValueOfPath(arr, cacheLevel1))
                            .min()
                            .orElseThrow();
                    cache.put(new Move(b1, b2), bestValue);
                }
            }
        }
        return Collections.unmodifiableMap(cache);

    }
    private static long getValueOfPath( Dir[] path, Map<Move, Long> cache){
        long sum = cache.get(new Move(Dir.A, path[0]));
        for(int i = 1; i < path.length; i++){
            sum += cache.get(new Move(path[i - 1], path[i]));
        }
        sum += cache.get(new Move(path[path.length-1], Dir.A));
        return sum;
    }

    static void solve(Map<Move, Long> cache) throws IOException{
        var inputs = readInput();
        long total = 0L;
        for(var s : inputs){
            int prev = 10;
            long sum = 0L;
            for(int i = 0; i < s.length(); i++){
                int curr = s.charAt(i) - '0';
                if(i == s.length() - 1){
                    curr = 10;
                }
                var paths = paths(prev, curr);
                var bestValue = paths.stream()
                        .mapToLong(arr -> getValueOfPath(arr, cache))
                        .min()
                        .orElseThrow();
                sum += bestValue;
                prev = curr;
            }
            total += sum * Integer.parseInt(s.substring(0, s.length() - 1));
        }
        System.out.println(total);
    }
    public static void main(String[] args) throws IOException{
        var cacheLevel1 = computeCacheLevel1();
        var cacheLevel2 = computeCacheLevel2(cacheLevel1);

        solve(cacheLevel2);

    }
}
