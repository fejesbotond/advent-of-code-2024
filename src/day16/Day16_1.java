package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class Day16_1 {

    static final char START = 'S';
    static final char END = 'E';
    static final char OBSTACLE = '#';
    static final char FREE = '.';

    private static final String FILE_NAME = "puzzleInputs/day16.txt";

    static final IntTuple[] directions = new IntTuple[]{
            new IntTuple(0, 1),
            new IntTuple(0, -1),
            new IntTuple(1, 0),
            new IntTuple(-1, 0)
    };

    static InputData readInput() throws IOException {
        try(var br = new BufferedReader(new FileReader(FILE_NAME))){
            var lines = new ArrayList<String>();
            String line;
            while((line = br.readLine()) != null){
                lines.add(line);
            }

            var grid = new char[lines.size()][];
            IntTuple startPos = null;
            IntTuple endPos = null;
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                grid[i] = line.toCharArray();
                if(startPos == null){
                    var j = line.indexOf(START);
                    if(j != -1){
                        startPos = new IntTuple(i, j);
                    }
                }
                if(endPos == null){
                    var j = line.indexOf(END);
                    if(j != -1){
                        endPos = new IntTuple(i, j);
                    }
                }
            }
            return new InputData(grid, startPos, endPos);
        }
    }

    private static void solve() throws IOException{
        var inputData = readInput();
        var result = dijkstra(inputData.grid(), inputData.startPos(), inputData.endPos());
        System.out.println(result);
    }

    private static long dijkstra(char[][] grid, IntTuple startPos, IntTuple endPos){
        var idToDistance = new HashMap<Id, Long>();
        var queue = new PriorityQueue<>(Comparator.comparingLong(SearchNode::distance));
        var resultDistance = Long.MAX_VALUE;
        var endPosCounter = 0;
        final var neighboursOfEndPos = 2;

        var id = new Id(startPos, directions[0]);
        queue.add(new SearchNode(id, 0L));
        idToDistance.put(id, 0L);

        while(!queue.isEmpty()){
            var currentSearchNode = queue.remove();
            var currentPos = currentSearchNode.id().pos();
            var currentDir = currentSearchNode.id().dir();

            if(idToDistance.get(currentSearchNode.id()).equals(currentSearchNode.distance())){
                if(currentPos.equals(endPos)){
                    resultDistance = Math.min(currentSearchNode.distance(), resultDistance);
                    endPosCounter++;
                    if(endPosCounter == neighboursOfEndPos){
                        break;
                    }
                }
                for (IntTuple dir : directions) {
                    if((currentDir.x() == dir.x() || currentDir.y() == dir.y()) && !currentDir.equals(dir)){
                        continue;
                    }
                    var x = currentPos.x() + dir.x();
                    var y = currentPos.y() + dir.y();
                    if (grid[x][y] != OBSTACLE) {
                        var newPos = new IntTuple(x, y);
                        id = new Id(newPos, dir);
                        var newDistance = (currentDir.equals(dir) ? 1L : 1001L) + currentSearchNode.distance();
                        var actualMinDistance = idToDistance.get(id);
                        if (actualMinDistance == null || actualMinDistance > newDistance) {
                            idToDistance.put(id, newDistance);
                            queue.add(new SearchNode(id, newDistance));
                        }
                    }
                }
            }
        }
        return resultDistance;
    }

    public static void main(String[] args) throws IOException {
        var startTime = System.nanoTime();
        solve();
        var endTime = System.nanoTime();
        var timeElapsed = (endTime - startTime) / 1_000_000_000.0;
        System.out.println(timeElapsed + " s");
    }

}
