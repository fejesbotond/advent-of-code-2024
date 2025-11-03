package day19;

import java.io.IOException;
import java.util.List;

public class Day19_2 {
    private static void solve() throws IOException {
        var inputData = Day19_1.readInput();
        var result = inputData.candidates().stream()
                .mapToLong(c -> countPossibilities(c, inputData.words()))
                .sum();
        System.out.println(result);

    }
    static long countPossibilities(String text, List<String> words) {
        int n = text.length();
        long[] dp = new long[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (String w : words) {
                int len = w.length();
                if (len <= i && text.substring(i - len, i).equals(w)) {
                    dp[i] += dp[i - len];
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) throws IOException {
        solve();
    }
}
