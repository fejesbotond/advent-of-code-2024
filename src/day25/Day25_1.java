package day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day25_1 {
    private static final int N = 7;
    private static final int M = 5;

    private static int[] values(char[][] grid) {
        var result = new int[M];
        for(int j = 0; j < M; j++){
            int count = 0;
            for(int i = 0; i < N; i++){
                if(grid[i][j] == '#'){
                    count++;
                }
            }
            result[j] = count - 1;
        }
        return result;
    }


    private static void readInput(List<int[]> keys, List<int[]> locks) throws IOException {
        try(var br = new BufferedReader(new FileReader("puzzleInputs/day25.txt"))){
            while(true){
                var grid = new char[N][];
                for(int i = 0; i < N; i++){
                    grid[i] = br.readLine().toCharArray();
                }
                if(Arrays.equals(grid[0], "#####".toCharArray())){
                    locks.add(values(grid));
                }else{
                    keys.add(values(grid));
                }

                if(br.readLine() == null){
                    break;
                }
            }

        }
    }
    private static boolean checkOverlap(int[] key, int[] lock){
        for(int i = 0; i < key.length; i++){
            if(key[i] + lock[i] > 5){
                return false;
            }
        }
        return true;
    }
    private static void solve() throws IOException {
        var keys = new ArrayList<int[]>();
        var locks = new ArrayList<int[]>();
        readInput(keys, locks);
        var sum = 0;
        for(var key : keys){
            for(var lock : locks){
                if(checkOverlap(key, lock)){
                    sum++;
                }
            }
        }
        System.out.println(sum);

    }
    public static void main(String[] args) throws IOException {
        solve();

    }
}
