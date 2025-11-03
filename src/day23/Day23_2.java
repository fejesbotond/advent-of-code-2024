package day23;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day23_2 {

    private static class MaxCliqueFinder{

        private List<String> maxClique;
        private final List<String> vertices;
        private final Set<Set<String>> edgeTable;

        MaxCliqueFinder(List<String> vertices, Set<Set<String>> edgeTable){
            this.vertices = vertices;
            this.edgeTable = edgeTable;
            find(0, new ArrayList<>());
        }
        private void find(int idx, List<String> currentClique){
            for(int i = idx; i < vertices.size(); i++){
                String v = vertices.get(i);
                if(check(v, currentClique)){
                    currentClique.add(v);
                    if(maxClique == null || currentClique.size() > maxClique.size()){
                        maxClique = List.copyOf(currentClique);
                    }
                    find(i + 1, currentClique);
                    currentClique.removeLast();
                }
            }
        }
        private boolean check(String newVertex, List<String> currentClique){
            for(String v : currentClique){
                var newEdge = Set.of(v, newVertex);
                if(!edgeTable.contains(newEdge)){
                    return false;
                }
            }
            return true;
        }
        List<String> getMaxClique(){
            return maxClique;
        }
    }

    private static void solve() throws IOException {
        List<Set<String>> pairs = Day23_1.readInput();
        List<String> vertices = Day23_1.getVertices(pairs);
        var edgeTable = new HashSet<>(pairs);

        var maxCliqueFinder = new MaxCliqueFinder(vertices, edgeTable);
        List<String> result = maxCliqueFinder.getMaxClique();

        String formatted = result.stream().sorted().collect(Collectors.joining(","));

        System.out.println(formatted);
    }

    public static void main(String[] args) throws IOException{
        long s = System.nanoTime();
        solve();
        long e = System.nanoTime();
        double seconds = (e - s) / 1_000_000_000.0;
        String f = String.format("%.3f", seconds);
        System.out.println(f + " sec");
    }
}
