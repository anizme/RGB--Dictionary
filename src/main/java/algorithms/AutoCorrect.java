package algorithms;

import java.sql.SQLException;
import java.util.List;

import static controller.ApplicationStart.dictionaryDB;

public class AutoCorrect {
    public static String findNearestWord(String target) throws SQLException {
        List<String> allWords = dictionaryDB.getAllWords();

        String nearestWord = "";
        int minDistance = Integer.MAX_VALUE;

        for (String word : allWords) {
            int distance = misMatchDistance(target, word);
            if (distance < minDistance) {
                minDistance = distance;
                nearestWord = word;
            }
        }
        return nearestWord;
    }

    private static int misMatchDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int s1Len = s1.length();
        int s2Len = s2.length();
        int[][] dp = new int[s1Len + 1][s2Len + 1];

        for (int i = 0; i <= s1Len; i++) {
            for (int j = 0; j <= s2Len; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = minOfThree(
                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1
                    );
                }
            }
        }
        return dp[s1Len][s2Len];
    }

    private static int minOfThree(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }


}
