import java.util.*;

public class BoyerMoore {

    private static int[] computePrefixFunction(char[] P, int m) {
        int[] pi = new int[m + 1];
        pi[1] = 0;
        int k = 0;
        for (int i = 2; i <= m; i++) {
            while (k > 0 && P[k + 1] != P[i]) {
                k = pi[k];
            }
            if (P[k + 1] == P[i]) {
                k++;
            }
            pi[i] = k;
        }
        return pi;
    }

    private static int[] computeLastOccurrenceFunction(char[] P, int m) {
        int[] lambda = new int[256];
        
        Arrays.fill(lambda, 0);

        
        for (int j = 1; j <= m; j++) {
            
            lambda[P[j]] = j;
        }
        return lambda;
    }

    
    private static int[] computeGoodSuffixFunction(char[] P, int m) {
    
        int[] pi = computePrefixFunction(P, m);
        
        char[] P_prime = new char[m + 1];
        for (int i = 1; i <= m; i++) {
            P_prime[i] = P[m - i + 1];
        }

        int[] pi_prime = computePrefixFunction(P_prime, m);

        int[] gamma = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            gamma[j] = m - pi[m];
        }

        for (int l = 1; l <= m; l++) {
            int j = m - pi_prime[l];
            if (gamma[j] > l - pi_prime[l]) {
                gamma[j] = l - pi_prime[l];
            }
        }
        return gamma;
    }

    public static void boyerMooreMatcher(String textStr, String patternStr) {
        int n = textStr.length(); 
        int m = patternStr.length(); 

        char[] T = new char[n + 1];
        char[] P = new char[m + 1];
        for (int i = 0; i < n; i++) T[i + 1] = textStr.charAt(i);
        for (int i = 0; i < m; i++) P[i + 1] = patternStr.charAt(i);

        int[] lambda = computeLastOccurrenceFunction(P, m);

        int[] gamma = computeGoodSuffixFunction(P, m);

        int s = 0;

        while (s <= n - m) {
            int j = m;

            while (j > 0 && P[j] == T[s + j]) {
                j = j - 1;
            }

            if (j == 0) {
                System.out.println("Pattern occurs at shift " + s + " (Index: " + s + ")");
                s = s + gamma[0];
            } else {
                s = s + Math.max(gamma[j], j - lambda[T[s + j]]);
            }
        }
    }

    public static void main(String[] args) {
        String text = "Insertion sort typically has a smaller constant factor than merge sort";
        String pattern = "sort";

        boyerMooreMatcher(text, pattern);
    }
}
