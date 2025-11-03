package day2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day2_1 {

    static boolean check(List<Integer> list){
        if(list.size() == 1){
            return true;
        }
        int d = list.get(1) - list.get(0);
        if(d == 0){
            return false;
        }
        boolean increasing = d > 0;
        for(int i = 1;i<list.size();i++){
            int diff = list.get(i) - list.get(i-1);
            if(increasing && diff < 0){
                return false;
            }
            if(!increasing && diff > 0){
                return false;
            }
            diff = Math.abs(diff);
            if(diff < 1 || diff > 3){
                return false;
            }
        }
        return true;
    }

    static List<List<Integer>> readInput() throws IOException {
        try(var br = new BufferedReader(new FileReader("puzzleInputs/day2.txt"))){
            var data = new ArrayList<List<Integer>>();
            String line;
            while((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                var innerList = new ArrayList<Integer>();
                for (String s : split) {
                    innerList.add(Integer.parseInt(s));
                }
                data.add(innerList);
            }
            return data;
        }
    }

    public static void main(String[] args) throws IOException {
        var data = readInput();
        long c = data.stream().filter(Day2_1::check).count();
        System.out.println(c);
    }
}