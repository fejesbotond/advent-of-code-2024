package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day5_1 {

    static InputData readInput() throws IOException {
        try(var br = Files.newBufferedReader(Path.of("puzzleInputs", "day5.txt"))){
            var rules = new ArrayList<Rule>();
            var sequences = new ArrayList<List<Integer>>();
            String line;
            boolean readingRulesState = true;
            while((line = br.readLine()) != null){
                if(line.isEmpty()){
                    readingRulesState = !readingRulesState;
                }
                else if(readingRulesState){
                    var split = line.split("\\|");
                    rules.add(new Rule(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                }
                else{
                    var sequence = Arrays.stream(line.split(","))
                            .map(Integer::parseInt)
                            .toList();
                    sequences.add(sequence);
                }
            }
            return new InputData(rules, sequences);
        }
    }
    static boolean check(List<Integer> sequence, Set<Rule> rules){
        for(int i = 0; i < sequence.size() - 1; i++){
            for (int j = i + 1; j < sequence.size(); j++) {
                if(rules.contains(new Rule(sequence.get(j), sequence.get(i)))){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        var inputData = readInput();
        var rules = new HashSet<>(inputData.rules());
        var sequences = inputData.sequences();
        int sumResult = sequences.stream()
                .mapToInt(seq -> check(seq, rules) ? seq.get(seq.size() / 2): 0)
                .sum();
        System.out.println(sumResult);
    }
}
