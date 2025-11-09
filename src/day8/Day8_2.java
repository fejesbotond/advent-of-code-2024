package day8;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static day8.Day8_1.readInput;

public class Day8_2 {

    private static int calculateGCD(int a, int b) {
        int num1 = Math.abs(a), num2 = Math.abs(b);
        while (num2 != 0) {
            int temp = num2;
            num2 = num1 % num2;
            num1 = temp;
        }
        return num1;
    }

    private static int solve(InputData input) {
        Set<Position> set = new HashSet<>();

        for (var entry : input.data().entrySet()) {
            var list = entry.getValue();
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    int xd = list.get(i).x() - list.get(j).x();
                    int yd = list.get(i).y() - list.get(j).y();
                    int gcd = calculateGCD(xd, yd);
                    int nx = xd / gcd, ny = yd / gcd;

                    int curX = list.get(i).x(), curY = list.get(i).y();
                    while (curX >= 0 && curX < input.rows() && curY >= 0 && curY < input.cols()) {
                        set.add(new Position(curX, curY));
                        curX += nx;
                        curY += ny;
                    }

                    curX = list.get(j).x();
                    curY = list.get(j).y();
                    while (curX >= 0 && curX < input.rows() && curY >= 0 && curY < input.cols()) {
                        set.add(new Position(curX, curY));
                        curX -= nx;
                        curY -= ny;
                    }
                }
            }
        }

        return set.size();
    }

    public static void main(String[] args) throws IOException {
        var input = readInput();
        System.out.println(solve(input));
    }
}
