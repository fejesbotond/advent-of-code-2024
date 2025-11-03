package day24;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day24_2 {
    record G3(GateType gateType, String input1, String input2){}

    private static void doSwaps(List<Gate> gates, String[][] swaps){
        for(String[] swap : swaps){
            for(int i = 0; i< gates.size();i++){
                var gate = gates.get(i);
                var output = gate.output();
                if(output.equals(swap[0])){
                    gates.set(i, new Gate(gate.gateType(), gate.input1(), gate.input2(), swap[1]));
                }
                if(output.equals(swap[1])){
                    gates.set(i, new Gate(gate.gateType(), gate.input1(), gate.input2(), swap[0]));
                }
            }
        }
    }
    private static void visualizeCircuit(List<Gate> gates){
        var gatesByOutput = gates.stream()
                .collect(Collectors.toMap(
                        Gate::output,
                        g -> new G3(g.gateType(), g.input1(), g.input2())
                ));


        var fixedVars = IntStream.rangeClosed(0, 44)
                .mapToObj(i -> String.format("%02d", i))
                .flatMap(s -> Stream.of("x" + s, "y" + s))
                .collect(Collectors.toSet());

        var zVars = IntStream.rangeClosed(0, 45)
                .mapToObj(i -> "z" + String.format("%02d", i))
                .toList();

        var sb = new StringBuilder();
        String sep = System.lineSeparator();
        for (var zVar : zVars) {
            var currentFixedVars = fix(zVar, fixedVars, gatesByOutput);
            sb.append(sep).append("->").append(zVar).append(sep);
            for (var output : currentFixedVars) {
                var g3 = gatesByOutput.get(output);
                sb.append(g3.input1()).append(" ")
                        .append(g3.gateType()).append(" ")
                        .append(g3.input2()).append(" -> ")
                        .append(output).append(sep);
            }
            fixedVars.addAll(currentFixedVars);
        }
        System.out.println(sb);
    }

    private static List<String> fix(String var, Set<String> fixedVars, Map<String, G3> gatesByOutput){
        var var1 = gatesByOutput.get(var).input1();
        var var2 = gatesByOutput.get(var).input2();
        var c1 = fixedVars.contains(var1);
        var c2 = fixedVars.contains(var2);

        List<String> currentFixedVars;

        if(c1 && c2){
            currentFixedVars = new ArrayList<>();
        }else if(!c1 && c2){
            currentFixedVars = fix(var1, fixedVars, gatesByOutput);

        }else if(c1){
            currentFixedVars = fix(var2, fixedVars, gatesByOutput);
        }
        else{
            currentFixedVars = fix(var1, fixedVars, gatesByOutput);
            currentFixedVars.addAll(fix(var2, fixedVars, gatesByOutput));
        }
        currentFixedVars.add(var);
        return currentFixedVars;
    }
    public static void main(String[] args) throws IOException {
        var gates = Day24_1.readInput().gates();

        visualizeCircuit(gates);

        //TODO: automate finding the solution
        var swaps = new String[][] {
                {"z12", "kth"},
                {"z26", "gsd"},
                {"z32", "tbt"},
                {"qnf", "vpm"},
        };

        doSwaps(gates, swaps);

        visualizeCircuit(gates);

        var s = Arrays.stream(swaps).flatMap(Stream::of).sorted().collect(Collectors.joining(","));

        System.out.println(s);
    }
}
