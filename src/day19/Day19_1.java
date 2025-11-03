package day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Day19_1 {

    record InputData(List<String> candidates, List<String> words){}

    static boolean check(String text, List<String> words){
        String pattern = "^(?:" + String.join("|", words) + ")*$";
        return Pattern.matches(pattern, text);

    }
    public static void main(String[] args) throws IOException {
        var inputData = readInput();
        var count = inputData.candidates.stream().filter(c -> check(c, inputData.words)).count();
        System.out.println(count);
    }
    static InputData readInput() throws IOException {
        var candidates = new ArrayList<String>();
        List<String> words;
        try(var br = new BufferedReader(new FileReader("puzzleInputs/day19.txt"))){
            String line = br.readLine();
            words = new ArrayList<>(Arrays.asList(line.split(", ")));
            br.readLine();
            while((line = br.readLine()) != null){
                candidates.add(line);
            }
        }
        return new InputData(candidates, words);
    }
}
