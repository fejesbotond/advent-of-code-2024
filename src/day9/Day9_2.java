package day9;

import java.io.IOException;

import static day9.Day9_1.*;

public class Day9_2 {

    private static void mergeByFile(int[] array) {
        int i = array.length - 1;
        while (i >= 0 && array[i] == EMPTY_ID) {
            i--;
        }
        int currentId = array[i];
        while (currentId > 0) {
            int li, ri;
            int j = array.length - 1;

            while (true) {
                if (array[j] == currentId) {
                    ri = j;
                    while (j >= 0 && array[j] == currentId) {
                        j--;
                    }
                    li = j + 1;
                    break;
                }
                j--;
            }

            j = 0;
            while (j < array.length) {
                if (array[j] == EMPTY_ID) {
                    int li2 = j;
                    while (j < array.length && array[j] == EMPTY_ID) {
                        j++;
                    }
                    int ri2 = j - 1;
                    if (ri2 < li && (ri2 - li2) >= (ri - li)) {
                        for (int x = 0; x < (ri - li + 1); x++) {
                            int temp = array[li + x];
                            array[li + x] = array[li2 + x];
                            array[li2 + x] = temp;
                        }
                        break;
                    }
                }
                j++;
            }
            currentId--;
        }
    }
    public static void main(String[] args) throws IOException {
        var array = readInput();
        mergeByFile(array);
        long sum = 0L;
        for(int i = 0; i < array.length; i++){
            if(array[i] != EMPTY_ID){
                sum += (long) i * array[i];
            }
        }
        System.out.println(sum);
    }
}
