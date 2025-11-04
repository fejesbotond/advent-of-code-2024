package day16;

import java.io.IOException;
import java.util.*;

import static day16.Day16_1.*;

public final class Day16_2 {

    private static void solve() throws IOException{
        var inputData = readInput();
        var count = countFreeNeighbourCells(inputData.endPos(), inputData.grid());
        var resultTuple = dijkstra(inputData.grid(), inputData.startPos(), inputData.endPos(), count);
        var coordinates = countPathCoordinates(resultTuple.idToPrevHopIds, resultTuple.idToDistance, inputData.endPos());
        System.out.println(coordinates.size());
    }

    record Result(Map<Id, List<Id>> idToPrevHopIds, Map<Id, Long> idToDistance){}

    private static int countFreeNeighbourCells(IntTuple pos, char[][] grid){
        var count = 0;
        for(IntTuple dir: directions){
            var x = pos.x() + dir.x();
            var y = pos.y() + dir.y();
            if(grid[x][y] == FREE){
                count++;
            }
        }
        return count;
    }

    private static Result dijkstra(char[][] grid, IntTuple startPos, IntTuple endPos, int countOfEndPosNeighbourFreeCells){
        var idToDistance = new HashMap<Id, Long>();
        var idToPrevHopIds = new HashMap<Id, List<Id>>();
        var queue = new PriorityQueue<>(Comparator.comparingLong(SearchNode::distance));
        var endPosCounter = 0;

        var id = new Id(startPos, directions[0]);
        queue.add(new SearchNode(id, 0L));
        idToDistance.put(id, 0L);
        idToPrevHopIds.put(id, Collections.emptyList());

        while(!queue.isEmpty()){
            var currentSearchNode = queue.remove();
            var currentPos = currentSearchNode.id().pos();
            var currentDir = currentSearchNode.id().dir();

            if(idToDistance.get(currentSearchNode.id()) == currentSearchNode.distance()){
                if(currentPos.equals(endPos)){
                    if(++endPosCounter == countOfEndPosNeighbourFreeCells){
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
                            var arr = new ArrayList<Id>();
                            arr.add(currentSearchNode.id());
                            idToPrevHopIds.put(id, arr);
                            queue.add(new SearchNode(id, newDistance));
                        }
                        else if(actualMinDistance == newDistance){
                            idToPrevHopIds.get(id).add(currentSearchNode.id());
                        }
                    }
                }
            }
        }
        return new Result(idToPrevHopIds, idToDistance);
    }

    private static Set<IntTuple> countPathCoordinates(Map<Id, List<Id>> idToPrevHops, Map<Id, Long> idToDistance, IntTuple endPos) {
        var visitedCoordinates = new HashSet<IntTuple>();
        var visitedIds = new HashSet<Id>();

        var candidates = Arrays.stream(directions)
                .map(dir -> new Id(endPos, dir))
                .filter(idToDistance::containsKey)
                .toList();

        var minDistance = candidates.stream()
                .mapToLong(idToDistance::get)
                .min()
                .orElseThrow();

        var startPoints = candidates.stream()
                .filter(id -> idToDistance.get(id) == minDistance)
                .toList();

        Deque<Id> stack = new ArrayDeque<>(startPoints);

        while (!stack.isEmpty()) {
            var current = stack.pop();
            if (!visitedIds.add(current)) continue;

            visitedCoordinates.add(current.pos());

            var prevList = idToPrevHops.get(current);
            stack.addAll(prevList);
        }

        return visitedCoordinates;
    }

    public static void main(String[] args) throws IOException {
        var startTime = System.nanoTime();
        solve();
        var endTime = System.nanoTime();
        var timeElapsed = (endTime - startTime) / 1_000_000_000.0;
        System.out.println(timeElapsed + " s");
    }
}
