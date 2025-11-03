package day2;

import java.io.IOException;
import java.util.List;

public class Day2_2 {

    private static boolean check(List<Integer> list){
        for(int i = 0;i<list.size();i++){
            int r = list.remove(i);
            if(Day2_1.check(list)){
                return true;
            }
            list.add(i, r);
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        var data = Day2_1.readInput();
        long c = data.stream().filter(Day2_2::check).count();
        System.out.println(c);
    }
}
