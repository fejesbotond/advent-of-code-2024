package day17;

import java.util.ArrayList;

public final class Day17_2 {

    private static final int[] PROGRAM_TEXT = new int[]{2,4,1,7,7,5,0,3,1,7,4,1,5,5,3,0};

    /*
        while(A != 0) {
            B = A % 8
            B = B ^ 7
            C = A / pow(2, B)
            A = A / 8
            B = B ^ 7
            B = B ^ C
            B = B % 8
            print B
        }

       The above program is equivalent to this:

       while(A != 0) {
           print (A ^ (A / pow(2, (A % 8) ^ 7))) % 8
           A = A / 8
       }

       This can be simplified even more.
       Dividing by a power of 2 is equivalent to right shift with the exponent.
       x % 8 is equivalent to x & 7

       while(A != 0){
           print (A ^ (A >> (A & 7 ^ 7))) & 7
           A = A >> 3
       }

       At first the 3 msb bits can be determined.
       If we started by trying to determine the 3 lsb bits, we would not know the upper bits yet.
       After identifying all possible combinations of the first 3 MSB bits, we can find the candidates for the next 3 MSB bits by fixing each previously found candidate...
    */
    public static void main(String[] args){
        var programText = PROGRAM_TEXT;
        var previous = new ArrayList<Long>();
        previous.add(0L);
        for(int i = programText.length - 1; i >= 0; i--){
            var next = new ArrayList<Long>();
            for(long pa: previous){
                for(long candidate = 0L; candidate <= 7L; candidate++){
                    long a = (pa << 3) + candidate;
                    long v =  (a ^ (a >> (a & 7L ^ 7L))) & 7L;
                    if(v == programText[i]){
                        next.add(a);
                    }
                }
            }
            previous = next;
        }

        var min = previous.stream().min(Long::compareTo).orElseThrow();

        System.out.println(min);
    }
}
