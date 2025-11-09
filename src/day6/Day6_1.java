package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Day6_1 {

    static char[][] readInput() throws IOException{
        try(var lines = Files.lines(Path.of("puzzleInputs", "day6.txt"))){
            return lines.map(String::toCharArray).toArray(char[][]::new);
        }
    }

    static Vec2 startPosition(char[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '^'){
                    return new Vec2(i, j);
                }
            }
        }
        throw new IllegalStateException("start position not found in input");
    }
    static Vec2 rotate90(Vec2 v) {
        return new Vec2(v.y(), -v.x());
    }
    static Set<Vec2> basePath(char[][] grid, Vec2 startPosition) {
        int n = grid.length;
        int m = grid[0].length;
        Set<Vec2> visitedPositions = new HashSet<>();

        var position = startPosition;
        var dir = new Vec2(-1, 0);

        while (position.x() >= 0 && position.x() < n &&
                position.y() >= 0 && position.y() < m) {

            if (grid[position.x()][position.y()] == '#') {
                position = new Vec2(position.x() - dir.x(), position.y() - dir.y());
                dir = rotate90(dir);
            } else {
                visitedPositions.add(position);
                position = new Vec2(position.x() + dir.x(), position.y() + dir.y());
            }
        }

        return visitedPositions;
    }

    public static void main(String[] args) throws IOException {
        var grid = readInput();
        var path = basePath(grid, startPosition(grid));
        System.out.println(path.size());
    }
}
