package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Day3_1 {

    public static void main(String[] args) throws IOException {
        var text = Files.readString(Path.of("puzzleInputs", "day3.txt"));
        var regex = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        var sum = regex.matcher(text).results()
                .mapToInt(mr ->
                    Integer.parseInt(mr.group(1)) * Integer.parseInt(mr.group(2))
                )
                .sum();
        System.out.println(sum);
    }
}
