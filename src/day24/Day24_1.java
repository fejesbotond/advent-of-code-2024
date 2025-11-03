package day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day24_1 {

    private static int zCounter(List<Gate> gates){
        return (int)gates.stream().filter(g -> g.output().startsWith("z")).count();
    }

    static void solve() throws IOException{
        var inputData = readInput();
        var gates = inputData.gates();
        var evaluatedMap = inputData.initialInputs();
        final var maxCount = zCounter(gates);
        var count = 0;
        while (count != maxCount){
            for(var gate : gates){
                if(!evaluatedMap.containsKey(gate.output())){
                    var bit1 = evaluatedMap.get(gate.input1());
                    var bit2 = evaluatedMap.get(gate.input2());
                    if(bit2 != null && bit1 != null){
                        var outBit = switch (gate.gateType()){
                            case AND -> bit1 & bit2;
                            case OR -> bit1 | bit2;
                            case XOR -> bit1 ^ bit2;
                        };
                        evaluatedMap.put(gate.output(), outBit);
                        if(gate.output().startsWith("z")){
                            count++;
                        }
                    }
                }
            }
        }
        var result = evaluatedMap.entrySet().stream()
                .filter(e -> e.getKey().startsWith("z"))
                .sorted(Map.Entry.<String, Integer>comparingByKey().reversed())
                .map(e -> e.getValue().longValue())
                .reduce(0L, (acc, bit) -> (acc << 1) | bit);

        System.out.println(result);
    }

    static InputData readInput() throws IOException {
        var initialInputs = new HashMap<String, Integer>();
        var gates = new ArrayList<Gate>();
        try (var reader = Files.newBufferedReader(Path.of("puzzleInputs", "day24.txt"))) {
            String line;
            while (!Objects.equals(line = reader.readLine(), "")) {
                var splitLine = line.split(": ");
                initialInputs.put(splitLine[0], Integer.parseInt(splitLine[1]));
            }
            while((line = reader.readLine()) != null){
                var splitLine = line.split(" ");
                var gateType = switch (splitLine[1]){
                    case "AND" -> GateType.AND;
                    case "XOR" -> GateType.XOR;
                    case "OR" -> GateType.OR;
                    default -> throw new IllegalArgumentException();
                };
                var i1 = splitLine[0];
                var i2 = splitLine[2];
                var out = splitLine[4];
                gates.add(new Gate(gateType, i1, i2, out));
            }
        }
        return new InputData(initialInputs, gates);
    }
    public static void main(String[] args) throws IOException{
        solve();

    }
}
