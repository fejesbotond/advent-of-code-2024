package day19;

import java.io.IOException;
import java.util.List;

public class Day19_1_dp {
    private static boolean check(String text, List<String> words){
        int n = text.length();
        var dp = new boolean[n + 1];
        dp[0] = true;
        for(int i = 1; i <= n; i++){
            for(String word : words){
                if(dp[i]){
                    break;
                }
                int startIndex = i - word.length();
                if(startIndex >= 0){
                    dp[i] = text.substring(startIndex, i).equals(word) && dp[startIndex];
                }
            }
        }
        return dp[n];

    }
    private static void solve() throws IOException {
        var inputData = Day19_1.readInput();
        var result = inputData.candidates()
                .stream()
                .filter(c -> check(c, inputData.words()))
                .count();

        System.out.println(result);
    }
    public static void main(String[] args) throws IOException{
        solve();
    }
}
