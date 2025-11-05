package day12;

import java.io.IOException;
import java.util.*;

import static day12.Day12_1.*;

public class Day12_2 {

    private static void addToMap(int key, int value, Map<Integer, List<Integer>> map){
        var columns = map.computeIfAbsent(key, _ -> new ArrayList<>());
        int index = Collections.binarySearch(columns, value);
        index = -(index + 1);
        columns.add(index, value);
    }

    private static int countSwitches(List<Integer> orderedList){
         int count = 1;
         for(int i = 1;i<orderedList.size();i++){
             if(orderedList.get(i) != orderedList.get(i-1)+1){
                 count++;
             }
         }
         return  count;
    }
    private static long countAll(Map<Integer, List<Integer>> map){
         long count = 0;
         for(List<Integer> list : map.values()){
             count += countSwitches(list);
         }
         return  count;
    }

    private static long explore(char[][] grid, boolean[][] visited, int startX, int startY){
        int rows = grid.length;
        int cols = grid[0].length;
        char targetChar = grid[startX][startY];

        long perimeter = 0;
        long area = 0;

        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        Map<Integer, List<Integer>> columnToRowsLeft = new HashMap<>();
        Map<Integer, List<Integer>> columnToRowsRight = new HashMap<>();
        Map<Integer, List<Integer>> rowToColumnsUp = new HashMap<>();
        Map<Integer, List<Integer>> rowToColumnsDown = new HashMap<>();


        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0];
            int y = cell[1];

            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && grid[nx][ny] == targetChar) {
                    if (!visited[nx][ny]) {
                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny});
                    }
                }
                else{
                    if(dir[0] == 1){
                        addToMap(x, y, rowToColumnsDown);
                    }
                    else if(dir[0] == -1){
                        addToMap(x, y, rowToColumnsUp);
                    }
                    else if(dir[1] == 1){
                        addToMap(y, x, columnToRowsRight);
                    }
                    else{
                        addToMap(y, x, columnToRowsLeft);
                    }
                }
            }
            area++;
        }
        perimeter += countAll(rowToColumnsDown);
        perimeter += countAll(rowToColumnsUp);
        perimeter += countAll(columnToRowsRight);
        perimeter += countAll(columnToRowsLeft);

        return perimeter * area;
    }
    public static void main(String[] args) throws IOException{
        var start = System.nanoTime();
        var grid = readInput();
        int rows = grid.length;
        int cols = grid[0].length;

        var visited = new boolean[rows][cols];
        long totalSum = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    totalSum += explore(grid, visited, i, j);
                }
            }
        }

        var end = System.nanoTime();
        var timeInMs = (double)(end - start) / 1_000_000.0;

        System.out.println(totalSum);
        System.out.println(("Time: " + timeInMs + " ms"));
    }
}
