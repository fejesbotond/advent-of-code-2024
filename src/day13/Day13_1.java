package day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13_1 {

    static List<long[]> readInput() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("puzzleInputs/day13.txt"))){
            var data = new ArrayList<long[]>();
            String line;
            while(true){
                long[] cs = new long[6];
                line = br.readLine();
                cs[0] = Long.parseLong(line.substring(12, 14));
                cs[1] = Long.parseLong(line.substring(18));
                line = br.readLine();
                cs[2] = Long.parseLong(line.substring(12, 14));
                cs[3] = Long.parseLong(line.substring(18));
                line = br.readLine();
                String[] t = line.split("=");
                String[] tt = t[1].split(",");
                cs[4] = Long.parseLong(tt[0]);
                cs[5] = Long.parseLong(t[2]);
                data.add(cs);
                if(br.readLine() == null){
                    break;
                }
            }
            return data;
        }
    }
    static long solveOne(long[] constants){
        long a1 = constants[0];
        long a2 = constants[1];
        long b1 = constants[2];
        long b2 = constants[3];
        long c1 = constants[4];
        long c2 = constants[5];
        long x = 0;
        long y = 0;
        long numerator = c1*b2 - c2*b1;
        long denominator = a1*b2 - a2*b1;
        if(numerator % denominator == 0){
            x = numerator / denominator;
        }
        numerator = a1*c2 - a2*c1;
        if(numerator % denominator == 0){
            y = numerator / denominator;
        }
        return x*3 + y;

    }
   //Check whether there are any cases when the buttons' vectors are parallel.
   //If there is such case, then if these vectors are also parallel with the prize's vector, there can be more possible solutions to reach the prize with the 2 parallel vectors.
    //(then extended gcd algorithm)
   
    static void analyzeData(List<long[]> data){
        for(long[] constants : data){
            if(constants[2] > constants[0]){
                if(constants[2] % constants[0] == 0){
                    long d = constants[2] / constants[0];
                    if(constants[3] % constants[1] == 0 && (constants[3] / constants[1]) == d){
                        System.out.println("x1: "+constants[0] + " y1: " + constants[1] + " x2: "+constants[2] + " y2: "+constants[3]);
                    }
                }
            }else{
                if(constants[0] % constants[2] == 0){
                    long d = constants[0] / constants[2];
                    if(constants[1] % constants[3] == 0 && (constants[1] / constants[3]) == d){
                        System.out.println("x1: "+constants[0] + " y1: " + constants[1] + " x2: "+constants[2] + " y2: "+constants[3]);
                    }
                }
            }

        }
    }

    public static void main(String[] args) throws IOException {
        var data = readInput();
        long sum = data.stream().mapToLong(Day13_1::solveOne).sum();
        System.out.println(sum);
    }
}