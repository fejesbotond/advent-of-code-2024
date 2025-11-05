package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day12_1 {

    static int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    private static void explore(char[][] grid, boolean[][] visited, int x, int y, char symbol, int[] acc) {
        visited[x][y] = true;
        acc[0]++;

        int rows = grid.length;
        int cols = grid[0].length;

        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx < 0 || nx >= rows || ny < 0 || ny >= cols || grid[nx][ny] != symbol) {
                acc[1]++;
            } else if (!visited[nx][ny]) {
                explore(grid, visited, nx, ny, symbol, acc);
            }
        }
    }

    static char[][] readInput() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("puzzleInputs", "day12.txt"));

        var grid = new char[lines.size()][];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    public static void main(String[] args) throws IOException {
        var grid = readInput();
        int rows = grid.length;
        int cols = grid[0].length;

        var visited = new boolean[rows][cols];
        int totalSum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    var areaAndPerimeter = new int[]{0, 0};
                    explore(grid, visited, i, j, grid[i][j], areaAndPerimeter);
                    totalSum += areaAndPerimeter[0] * areaAndPerimeter[1];
                }
            }
        }
        System.out.println(totalSum);
    }
}
