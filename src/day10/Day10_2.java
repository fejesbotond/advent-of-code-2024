package day10;

import java.io.IOException;

import static day10.Day10_1.readInput;

public class Day10_2 {

    private static int count(int[][] data, boolean[][] visited9s, int i, int j, int previousValue) {
        int rows = data.length;
        int cols = data[0].length;

        if (i < 0 || i >= rows || j < 0 || j >= cols || data[i][j] != previousValue + 1 || visited9s[i][j]) {
            return 0;
        }
        if (data[i][j] == 9) {
            return 1;
        }
        return count(data, visited9s, i + 1, j, data[i][j]) +
                count(data, visited9s, i - 1, j, data[i][j]) +
                count(data, visited9s, i, j + 1, data[i][j]) +
                count(data, visited9s, i, j - 1, data[i][j]);
    }

    private static int solve(int[][] data) {
        int rows = data.length;
        int cols = data[0].length;
        int sum = 0;
        var visited9s = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (data[i][j] == 0) {
                    sum += (count(data, visited9s, i + 1, j, 0) +
                            count(data, visited9s, i - 1, j, 0) +
                            count(data, visited9s, i, j + 1, 0) +
                            count(data, visited9s, i, j - 1, 0));
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        int[][] data = readInput();
        int result = solve(data);
        System.out.println(result);
    }
}
