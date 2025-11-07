package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day9_1 {
     static final int EMPTY_ID = -1;

    static int[] readInput() throws IOException {
        String input = Files.readString(Paths.get("puzzleInputs","day9.txt")).trim();

        int s = 0;
        for (char c : input.toCharArray()) {
            s += Character.digit(c, 10);
        }

        int[] arr = new int[s];
        int idx = 0;

        for (int i = 0; i < input.length(); i++) {
            int k = Character.digit(input.charAt(i), 10);
            if (i % 2 == 0) {
                while (k > 0) {
                    arr[idx++] = i / 2;
                    k--;
                }
            } else {
                while (k > 0) {
                    arr[idx++] = EMPTY_ID;
                    k--;
                }
            }
        }
        return arr;
    }

    private static void mergeByBlock(int[] array) {
        int li = 0;
        int ri = array.length - 1;

        while (array[li] != EMPTY_ID) {
            li++;
        }
        while (array[ri] == EMPTY_ID) {
            ri--;
        }

        while (ri > li) {
            int temp = array[ri];
            array[ri] = array[li];
            array[li] = temp;

            while (li < array.length && array[li] != EMPTY_ID) {
                li++;
            }
            while (ri >= 0 && array[ri] == EMPTY_ID) {
                ri--;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        var array = readInput();
        mergeByBlock(array);
        long sum = 0L;
        for(int i = 0; i < array.length; i++){
            if(array[i] != EMPTY_ID){
                sum += (long) i * array[i];
            }
        }
        System.out.println(sum);
    }
}
