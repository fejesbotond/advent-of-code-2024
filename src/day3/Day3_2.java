package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Day3_2 {

    public static void main(String[] args) throws IOException {
        var text = Files.readString(Path.of("puzzleInputs", "day3.txt"));
        var regex = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");
        var matches = regex.matcher(text).results().toList();
        boolean isDoState = true;
        int sum = 0;
        for(var match : matches){
            switch (match.group()){
                case "don't()" -> isDoState = false;
                case "do()" -> isDoState = true;
                default -> {
                    if(isDoState){
                        sum += Integer.parseInt(match.group(1)) * Integer.parseInt(match.group(2));
                    }
                }
            }
        }
        System.out.println(sum);
    }
}
