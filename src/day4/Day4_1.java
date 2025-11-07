package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day4_1 {

    static char[][] readInput() throws IOException {
        try(var lines = Files.lines(Path.of("puzzleInputs", "day4.txt"))){
            return lines.map(String::toCharArray).toArray(char[][]::new);
        }
    }

    public static void main(String[] args) throws IOException {
        var grid = readInput();
        int n = grid.length, m = grid[0].length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m - 3; j++) {
                char[] s = { grid[i][j], grid[i][j+1], grid[i][j+2], grid[i][j+3] };
                if(Arrays.equals(s, "XMAS".toCharArray()) || Arrays.equals(s, "SAMX".toCharArray())){
                    count++;
                }
            }
        }
        for (int i = 0; i < n - 3; i++) {
            for (int j = 0; j < m; j++) {
                char[] s = { grid[i][j], grid[i+1][j], grid[i+2][j], grid[i+3][j] };
                if(Arrays.equals(s, "XMAS".toCharArray()) || Arrays.equals(s, "SAMX".toCharArray())){
                    count++;
                }
            }
        }
        for (int i = 0; i < n - 3; i++) {
            for (int j = 0; j < m - 3; j++) {
                char[] s = { grid[i][j], grid[i+1][j+1], grid[i+2][j+2], grid[i+3][j+3] };
                if(Arrays.equals(s, "XMAS".toCharArray()) || Arrays.equals(s, "SAMX".toCharArray())){
                    count++;
                }
            }
        }
        for (int i = 0; i < n - 3; i++) {
            for (int j = 0; j < m - 3; j++) {
                char[] s = { grid[i][j+3], grid[i+1][j+2], grid[i+2][j+1], grid[i+3][j] };
                if(Arrays.equals(s, "XMAS".toCharArray()) || Arrays.equals(s, "SAMX".toCharArray())){
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
