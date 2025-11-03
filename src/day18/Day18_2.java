package day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day18_2 {


    private static void updateGrid(IntTuple newCoordinate, boolean[][] grid){
        grid[newCoordinate.y()][newCoordinate.x()] = true;
    }

    private static List<IntTuple> readInput() throws IOException {
        var list = new ArrayList<IntTuple>();
        try(var br = new BufferedReader(new FileReader("puzzleInputs/day18.txt"))){
            String line;
            while((line = br.readLine()) != null){
                var t = line.split(",");
                int x = Integer.parseInt(t[0]);
                int y = Integer.parseInt(t[1]);
                list.add(new IntTuple(x, y));
            }
        }
        return list;
    }

    private static void solve() throws IOException {
        List<IntTuple> candidates = readInput();
        var grid = new boolean[Day18_1.GRID_SIZE][Day18_1.GRID_SIZE];
        IntTuple result = null;
        for(IntTuple c : candidates){
            updateGrid(c, grid);
            int d = Day18_1.dijkstra(grid);
            if(d == -1){
                result = c;
                break;
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        long st = System.nanoTime();
        solve();
        double elapsedTime = (System.nanoTime() - st) / 1_000_000.0;
        System.out.println(elapsedTime + " ms");
    }
}
