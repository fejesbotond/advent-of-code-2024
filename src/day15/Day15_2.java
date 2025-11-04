package day15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static day15.Day15_1.*;

public class Day15_2 {

    static final char BOX_LEFT = '[';
    static final char BOX_RIGHT = ']';

    public static void main(String[] args) throws IOException {
        solve();
    }

    private static void solve() throws IOException {
        var data = updateInputData(readInput());
        var cur = data.startPosition();
        for (int i = 0; i < data.moves().length(); i++) {
            cur = step(data.grid(), toDirection(data.moves().charAt(i)), cur);
        }
        var resultValue = calculateResult(data.grid());
        System.out.println(resultValue);
    }

    private static int calculateResult(char[][] grid){
        var sum = 0;
        for(int i = 0;i<grid.length; i++){
            for(int j = 0;j<grid[i].length; j++){
                if(grid[i][j] == BOX_LEFT){
                    sum += i*100 + j;
                }
            }
        }
        return sum;
    }

    private static InputData updateInputData(InputData inputData){
        char[][] newGrid = new char[inputData.grid().length][inputData.grid()[0].length * 2];
        for (int i = 0; i < inputData.grid().length; i++) {
            for (int j = 0; j < inputData.grid()[0].length; j++) {
                char c = inputData.grid()[i][j];
                switch (c){
                    case WALL, FREE -> {
                        newGrid[i][j*2] = c;
                        newGrid[i][j*2 + 1] = c;
                    }
                    case POS -> {
                        newGrid[i][j*2] = c;
                        newGrid[i][j*2 + 1] = FREE;
                    }
                    case BOX -> {
                        newGrid[i][j*2] = BOX_LEFT;
                        newGrid[i][j*2 + 1] = BOX_RIGHT;
                    }
                }
            }
        }
        return new InputData(
            newGrid,
            inputData.moves(),
            new IntTuple(inputData.startPosition().x(), inputData.startPosition().y()*2)
        );
    }

    private static IntTuple step(char[][] grid, IntTuple dir, IntTuple currentPosition){
        var nextPosition = currentPosition;
        var coordinates = collectCoordinates(grid, dir, currentPosition);
        if(coordinates != null){
            translate(coordinates, grid, dir, currentPosition);
            nextPosition = new IntTuple(currentPosition.x() + dir.x(), currentPosition.y() + dir.y());
        }
        return nextPosition;
    }

    private static void translate(List<IntTuple> coordinates, char[][] grid, IntTuple dir, IntTuple currentPosition){
        coordinates.reversed().forEach(p -> {
            grid[p.x()+dir.x()][p.y()+dir.y()] = grid[p.x()][p.y()];
            grid[p.x()][p.y()] = FREE;
        });
        grid[currentPosition.x() + dir.x()][currentPosition.y() + dir.y()] = POS;
        grid[currentPosition.x()][currentPosition.y()] = FREE;
    }

    private static List<IntTuple> collectCoordinates(char[][] grid, IntTuple dir, IntTuple currentPosition){
        return dir.x() == 0 ? collectCoordinatesHorizontally(grid, dir, currentPosition)
                : collectCoordinatesVertically(grid, dir, currentPosition);
    }

    private static List<IntTuple> collectCoordinatesVertically(char[][] grid, IntTuple dir, IntTuple currentPosition) {
        var coordinates = new ArrayList<IntTuple>();
        var seen = new HashSet<IntTuple>();

        IntTuple first = new IntTuple(currentPosition.x() + dir.x(), currentPosition.y() + dir.y());

        switch (grid[first.x()][first.y()]) {
            case WALL -> {
                return null;
            }
            case BOX_LEFT, BOX_RIGHT -> {
                coordinates.add(first);
                seen.add(first);
            }
        }

        for (int i = 0; i < coordinates.size(); i++) {
            var p = coordinates.get(i);

            IntTuple sameLevel = (grid[p.x()][p.y()] == BOX_LEFT)
                    ? new IntTuple(p.x(), p.y() + 1)
                    : new IntTuple(p.x(), p.y() - 1);
            if (seen.add(sameLevel)) {
                coordinates.add(sameLevel);
            }

            IntTuple forward = new IntTuple(p.x() + dir.x(), p.y());
            switch (grid[forward.x()][forward.y()]) {
                case WALL -> {
                    return null;
                }
                case BOX_LEFT, BOX_RIGHT -> {
                    if (seen.add(forward)) {
                        coordinates.add(forward);
                    }
                }
            }
        }
        return coordinates;
    }

    private static List<IntTuple> collectCoordinatesHorizontally(char[][] grid, IntTuple dir, IntTuple currentPosition){
        var coordinates = new ArrayList<IntTuple>();
        final var x = currentPosition.x();
        var y = currentPosition.y() + dir.y() ;
        while (grid[x][y] != WALL && grid[x][y] != FREE){
            coordinates.add(new IntTuple(x, y));
            y += dir.y();
        }
        if(grid[x][y] == WALL){
            coordinates = null;
        }
        return coordinates;
    }

}
