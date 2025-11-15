package com.stringalgorithms;

/**
* Class implementing the Knuth-Morris-Pratt (KMP) substring search algorithm.
* KMP is an efficient algorithm that avoids re-scanning
* text characters by using information about the pattern's structure. Its time
* complexity is O(N + M), where N is the length of the text, and M is the length of the pattern.
*/
public class KMP {
    
    /**
     * Method to build the prefix function array (LPS - Longest Proper Prefix 
     * which is also a Suffix). This is the PREPROCESSING phase.
     * The LPS array is the "memory" of the algorithm; lps[i] stores the length of the longest 
     * proper prefix of the pattern which is also a suffix of the substring pattern[0..i].
     * * Time complexity: O(M)
     * @param pattern The pattern for which the array is computed.
     * @return The LPS array.
     */
    private static int[] computeLPSArray(String pattern) {
        int M = pattern.length();
        int[] lps = new int[M];
        
        // len - length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        
        lps[0] = 0; // lps[0] is always 0, as there is no proper prefix for a single character
        
        while (i < M) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                // Case 1: Characters match. Extend the prefix-suffix.
                len++;
                lps[i] = len;
                i++;
            } else { 
                // Case 2: Mismatch.
                if (len != 0) {
                    // Use the value from the LPS array to "fall back"
                    // to the previous longest prefix suffix.
                    // We do not increment i (text character), we only shift the pattern (decrease len).
                    len = lps[len - 1];
                } else { 
                    // Case 3: len == 0. No previous prefix suffix,
                    // and the current character did not match. Move to the next character in the pattern.
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    /**
     * The main substring search function using the KMP approach. 
     * This is the SEARCH phase.
     * * Time complexity: O(N)
     * Overall KMP time complexity: O(N + M)
     * * @param text The text (N) in which we search.
     * @param pattern The pattern (M) we are searching for.
     * @return The index of the first occurrence of the pattern in the text or -1 if not found.
     */
    public static int search(String text, String pattern) {
        int N = text.length();
        int M = pattern.length();

        if (M == 0) return 0; 
        if (N == 0) return -1;
        
        // Step 1: Preprocessing - compute the lps array.
        int[] lps = computeLPSArray(pattern);
        
        // Step 2: Search.
        int i = 0; // Index for text (T)
        int j = 0; // Index for pattern (P)
        
        while (i < N) {
            if (pattern.charAt(j) == text.charAt(i)) {
                // Case 1: Match. Move both pointers.
                i++;
                j++;
            }
            
            if (j == M) {
                // Case 2: Pattern found!
                // Index of the start of the match: i - j.
                return i - j;
                // If searching for all occurrences, update j here: j = lps[j-1];
            } else if (i < N && pattern.charAt(j) != text.charAt(i)) {
                // Case 3: Mismatch. 
                if (j != 0) {
                    // Apply KMP optimization: do not move the index i in the text backward.
                    // Move the index j in the pattern using lps[j-1].
                    j = lps[j - 1];
                } else {
                    // j == 0. Mismatch at the first character of the pattern.
                    // Simply move to the next character in the text.
                    i++;
                }
            }
        }
        
        // Pattern was not found after scanning the entire text.
        return -1;
    }
}