package day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day15_1 {
    static final String FILE_NAME = "puzzleInputs/day15.txt";

    static final char WALL = '#';
    static final char BOX = 'O';
    static final char FREE = '.';
    static final char POS = '@';
    static final char UP = '^';
    static final char DOWN = 'v';
    static final char LEFT = '<';
    static final char RIGHT = '>';

    static IntTuple toDirection(char c){
        return switch (c){
            case UP -> new IntTuple(-1, 0);
            case DOWN -> new IntTuple(1, 0);
            case LEFT -> new IntTuple(0,-1);
            case RIGHT -> new IntTuple(0, 1);
            default -> throw new IllegalStateException();
        };
    }

    private static IntTuple step(IntTuple dir, IntTuple currentPosition, char[][] grid){
        var nextPosition = currentPosition;
        int x = currentPosition.x(), y = currentPosition.y();
        while (grid[x][y] != WALL && grid[x][y] != FREE){
            x += dir.x();
            y += dir.y();
        }
        if(grid[x][y] == FREE){
            grid[x][y] = grid[x-dir.x()][y-dir.y()];
            grid[currentPosition.x()][currentPosition.y()] = FREE;
            grid[currentPosition.x() + dir.x()][currentPosition.y() + dir.y()] = POS;
            nextPosition = new IntTuple(currentPosition.x() + dir.x(), currentPosition.y() + dir.y());
        }
        return nextPosition;
    }

    private static int calcResult(char[][] grid){
        int sum = 0;
        for(int i = 0;i<grid.length; i++){
            for(int j = 0;j<grid[i].length; j++){
                if(grid[i][j] == BOX){
                    sum += i*100 + j;
                }
            }
        }
        return sum;
    }

    static InputData readInput() throws IOException {
        try (var br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            var lines = new ArrayList<String>();
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                lines.add(line);
            }
            var grid = new char[lines.size()][];
            IntTuple startPosition = null;
            var i = 0;
            for (var l : lines) {
                int j = l.indexOf(POS);
                if (j != -1) {
                    startPosition = new IntTuple(i, j);
                }
                grid[i++] = l.toCharArray();
            }
            var sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            var moves = sb.toString();
            return new InputData(grid, moves, startPosition);
        }
    }

    private static void solve() throws IOException{
        var inputData = readInput();

        var cur = inputData.startPosition();
        for(char c : inputData.moves().toCharArray()){
            cur = step(toDirection(c), cur, inputData.grid());
        }

        int result = calcResult(inputData.grid());
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException{
        solve();
    }
}
