package day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day20_1 {
    static final char WALL = '#';
    static final char FREE = '.';
    static final char START = 'S';
    static final char END = 'E';

    static final IntTuple[] directions = new IntTuple[]{
            new IntTuple(0, 1),
            new IntTuple(0, -1),
            new IntTuple(1, 0),
            new IntTuple(-1, 0)
    };


    static char[][] readInput() throws IOException {
        return Files.readAllLines(Path.of("puzzleInputs","day20.txt"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    static List<IntTuple> path(char[][] grid) {
        final var path = new ArrayList<IntTuple>();
        final var end = getPos(grid, END);

        var curr = getPos(grid, START);
        while (!curr.equals(end)) {

            var c = curr;
            var last = path.isEmpty() ? null : path.getLast();

            path.add(curr);

            curr = Arrays.stream(directions)
                    .map(d -> new IntTuple(c.x() + d.x(), c.y() + d.y()))
                    .filter(n -> grid[n.x()][n.y()] != WALL && !n.equals(last))
                    .findFirst()
                    .orElseThrow();
        }

        path.add(curr);

        return path;
    }



    private static IntTuple getPos(char[][] grid, char c) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] == c) {
                    return new IntTuple(x, y);
                }
            }
        }
        return new IntTuple(-1, -1);
    }

    private static void solve() throws IOException{
        char[][] grid = readInput();
        List<IntTuple> path = path(grid);

        var result = 0;
        for(int i = 0; i < path.size() - 3; i++){
            var curr = path.get(i);
            for(var dir1: directions){
                if(grid[curr.x() + dir1.x()][curr.y() + dir1.y()] != WALL){
                    continue;
                }
                for(var dir2 : directions){
                    int coordX = curr.x() + dir1.x() + dir2.x();
                    int coordY = curr.y() + dir1.y() + dir2.y();
                    var t = new IntTuple(coordX, coordY);

                    if(coordX >= 0 && coordY >= 0 && coordX < grid.length && coordY < grid[0].length
                        && grid[coordX][coordY] != WALL && !curr.equals(t)
                    ){
                        int idx = path.indexOf(t);
                        int distance = idx - i;
                        int saves = distance - 2;
                        if(saves >= 100){
                            result++;
                        }
                    }
                }
            }
        }
        System.out.println(result);

    }
    public static void main(String[] args) throws IOException{
        System.out.println("Day 20 - part 1");
        long s = System.nanoTime();
        solve();
        long e = System.nanoTime();
        double seconds = (e - s) / 1_000_000_000.0;
        String f = String.format("%.3f", seconds);
        System.out.println(f + " sec");

    }
}
