package day6;

import java.io.IOException;
import java.util.HashSet;

import static day6.Day6_1.*;

public class Day6_2 {

    record Pair(Vec2 v1, Vec2 v2){}

    static boolean check(char[][] grid, Vec2 startPosition) {
        int n = grid.length;
        int m = grid[0].length;

        boolean looped = false;
        boolean out = false;

        var visitedLines = new HashSet<Pair>();

        Vec2 position = startPosition;
        Vec2 previousPosition = new Vec2(-1, -1);
        Vec2 dir = new Vec2(-1, 0);

        while (!looped && !out) {
            if (position.x() < 0 || position.x() >= n ||
                    position.y() < 0 || position.y() >= m) {
                out = true;
            } else {
                char cell = grid[position.x()][position.y()];
                if (cell == '#') {
                    position = new Vec2(position.x() - dir.x(), position.y() - dir.y());
                    dir = rotate90(dir);
                    position = new Vec2(position.x() + dir.x(), position.y() + dir.y());
                } else {
                    if (!visitedLines.add(new Pair(position, previousPosition))) {
                        looped = true;
                    } else {
                        previousPosition = position;
                        position = new Vec2(position.x() + dir.x(), position.y() + dir.y());
                    }
                }
            }
        }
        return looped;
    }
    public static void main(String[] args) throws IOException {
        var grid = readInput();
        var path = basePath(grid, startPosition(grid));
        var startPosition = startPosition(grid);

        long count = path.stream().filter(pos -> {
            grid[pos.x()][pos.y()] = '#';
            boolean e = check(grid, startPosition);
            grid[pos.x()][pos.y()] = '.';
            return e;
        }).count();

        System.out.println(count);
    }
}
