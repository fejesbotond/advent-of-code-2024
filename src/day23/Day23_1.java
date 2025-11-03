package day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day23_1 {

    static List<Set<String>> readInput() throws IOException {
        var pairs = new ArrayList<Set<String>>();
        try(var br = new BufferedReader(new FileReader("puzzleInputs/day23.txt"))){
            String line;
            while((line = br.readLine()) != null){
                var t = line.split("-");
                pairs.add(Set.of(t));
            }
        }
        return pairs;
    }
    static List<String> getVertices(List<Set<String>> pairs){
        var set = new HashSet<String>();
        pairs.forEach(set::addAll);
        return new ArrayList<>(set);
    }
    private static void solve() throws IOException {
        List<Set<String>> pairs = readInput();
        var lookUpTable = new HashSet<>(pairs);
        List<String> vertices = getVertices(pairs);
        var result = new HashSet<Set<String>>();
        for(int j = 0; j < vertices.size() - 2; j++){
            String v1 = vertices.get(j);
            for (int k = j + 1; k < vertices.size() - 1; k++) {
                String v2 = vertices.get(k);
                var edge1 = Set.of(v1, v2);
                if(lookUpTable.contains(edge1)){
                    for (int l = k + 1; l < vertices.size(); l++) {
                        var v3 = vertices.get(l);
                        var edge2 = Set.of(v2, v3);
                        var edge3 = Set.of(v1, v3);
                        if(lookUpTable.contains(edge2) && lookUpTable.contains(edge3)){
                            result.add(Set.of(v1, v2, v3));
                        }
                    }
                }
            }
        }
        var count = result.stream()
                .filter(triple -> triple.stream().anyMatch(str -> str.startsWith("t")))
                .count();
        System.out.println(count);
    }
    public static void main(String[] args) throws IOException{
        solve();
    }
}
