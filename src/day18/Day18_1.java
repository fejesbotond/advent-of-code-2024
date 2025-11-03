package day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Day18_1 {
    private static final int[][] dirs = new int[][] {
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0}
    };
    static final int GRID_SIZE = 71;
    private static final int BYTE_LENGTH = 1024;
    private static final IntTuple endCoordinate = new IntTuple(GRID_SIZE-1, GRID_SIZE-1);

    private static boolean[][] readInput() throws IOException {
        var grid = new boolean[GRID_SIZE][GRID_SIZE];
        try(var br = new BufferedReader(new FileReader("puzzleInputs/day18.txt"))){
            String line;
            int c = 0;
            while((line = br.readLine()) != null && c < BYTE_LENGTH){
                var t = line.split(",");
                int x = Integer.parseInt(t[0]);
                int y = Integer.parseInt(t[1]);
                grid[y][x] = true;
                c++;
            }
        }
        return grid;
    }

    record Node(IntTuple coordinate, int distance){}

    static int dijkstra(boolean[][] grid){
        var settled = new HashSet<IntTuple>();
        var pq = new PriorityQueue<Node>(Comparator.comparingInt(n -> n.distance));
        pq.add(new Node(new IntTuple(0, 0), 0));
        while(!pq.isEmpty()){
            var node = pq.poll();
            if(node.coordinate.equals(endCoordinate)){
                return node.distance;
            }
            if(!settled.add(node.coordinate)){
                continue;
            }
            int x = node.coordinate.x();
            int y = node.coordinate.y();
            for(int[] dir : dirs){
                int nx = x + dir[1];
                int ny = y + dir[0];
                if(nx < 0 || nx > GRID_SIZE-1 || ny < 0 || ny > GRID_SIZE-1 || grid[ny][nx]){
                    continue;
                }
                pq.add(new Node(new IntTuple(nx, ny), node.distance + 1));
            }

        }
        return -1;
    }

    private static void solve() throws IOException{
        boolean[][] grid = readInput();
        int result = dijkstra(grid);
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        long st = System.nanoTime();
        solve();
        double elapsedTime = (System.nanoTime() - st) / 1_000_000.0;
        System.out.println(elapsedTime + " ms");
    }
}
