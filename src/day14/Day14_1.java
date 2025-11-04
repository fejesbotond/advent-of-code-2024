package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day14_1 {

    static final int n = 103;
    static final int m = 101;

    public static void main(String[] args) throws IOException{
        var robots = readInput();
        int[][] grid = simulate(robots);

        int c1 = 0, c2 = 0, c3 = 0, c4 = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(i < n/2 && j < m/2) c1 += grid[i][j];
                else if(i < n/2 && j > m/2) c2 += grid[i][j];
                else if(i > n/2 && j < m/2 ) c3 += grid[i][j];
                else if(i > n/2 && j > m/2 ) c4 += grid[i][j];
            }
        }
        int sum = c1*c2*c3*c4;
        System.out.println(sum);
    }

    static int[][] simulate(List<int[]> robots){
        var grid = new int[n][m];

        for (var r : robots) {
            int x = (r[0] + 100 * r[2]) % m;
            int y = (r[1] + 100 * r[3]) % n;
            if (x < 0) x += m;
            if (y < 0) y += n;
            grid[y][x]++;
        }

        return grid;
    }

    static List<int[]> readInput() throws IOException {
        var pattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
        try (var br = new BufferedReader(new FileReader("puzzleInputs/day14.txt"))) {
            return br.lines()
                    .map(line -> {
                        var matcher = pattern.matcher(line);
                        if (!matcher.find())
                            throw new IllegalArgumentException("Bad line: " + line);
                        return IntStream.rangeClosed(1, 4)
                                .map(i -> Integer.parseInt(matcher.group(i)))
                                .toArray();
                    })
                    .toList();
        }
    }
}
