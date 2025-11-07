package day4;


import java.io.IOException;
import java.util.Arrays;

import static day4.Day4_1.readInput;

public class Day4_2 {

    public static void main(String[] args) throws IOException {
        var grid = readInput();
        int count = 0;
        for(int i = 0; i < grid.length - 2; i++){
            for (int j = 0; j < grid[0].length - 2; j++) {
                char[] s1 = { grid[i][j], grid[i+1][j+1], grid[i+2][j+2] };
                char[] s2 = { grid[i][j+2], grid[i+1][j+1], grid[i+2][j] };

                if ((Arrays.equals(s1, "MAS".toCharArray()) || Arrays.equals(s1, "SAM".toCharArray())) &&
                        (Arrays.equals(s2, "MAS".toCharArray()) || Arrays.equals(s2, "SAM".toCharArray()))) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
