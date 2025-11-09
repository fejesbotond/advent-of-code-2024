package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day8_1 {

    static InputData readInput() throws IOException {
        var lines = Files.readAllLines(Path.of("puzzleInputs", "day8.txt"));
        var data = new HashMap<Character, List<Position>>();
        for (int x = 0; x < lines.size(); x++) {
            var line = lines.get(x);
            for (int y = 0; y < line.length(); y++) {
                char c = line.charAt(y);
                if (c != '.'){
                    data.computeIfAbsent(c, _ -> new ArrayList<>())
                            .add(new Position(x, y));

                }
            }
        }
        int rows = lines.size();
        int cols = lines.getFirst().length();
        return new InputData(data, rows, cols);
    }

    private static boolean inBounds(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    private static int solve(InputData input) {
        Set<Position> antinodes = new HashSet<>();

        for (var entry : input.data().entrySet()) {
            var antennas = entry.getValue();

            for (int i = 0; i < antennas.size() - 1; i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    var a = antennas.get(i);
                    var b = antennas.get(j);

                    int dx = b.x() - a.x();
                    int dy = b.y() - a.y();

                    int ax1 = a.x() - dx;
                    int ay1 = a.y() - dy;
                    if (inBounds(ax1, ay1, input.rows(), input.cols()))
                        antinodes.add(new Position(ax1, ay1));

                    int ax2 = b.x() + dx;
                    int ay2 = b.y() + dy;
                    if (inBounds(ax2, ay2, input.rows(), input.cols()))
                        antinodes.add(new Position(ax2, ay2));
                }
            }
        }

        return antinodes.size();
    }

    public static void main(String[] args) throws IOException {
        var input = readInput();
        System.out.println(solve(input));
    }
}
