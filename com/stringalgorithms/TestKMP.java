package com.stringalgorithms;

/**
* Class for testing the KMP algorithm.
* Contains test examples of varying lengths, as required by the task,
* and outputs results for analysis.
*/
public class TestKMP {
    
    /**
     * Structure for storing test data.
     */
    private static class TestCase {
        String name;
        String text;
        String pattern;
        int expectedIndex;

        public TestCase(String name, String text, String pattern, int expectedIndex) {
            this.name = name;
            this.text = text;
            this.pattern = pattern;
            this.expectedIndex = expectedIndex;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Testing the Knuth-Morris-Pratt (KMP) algorithm ---");
        System.out.println("--- Input and output for the report ---");

        // Requirement: three examples of strings of different lengths: short, medium, long.
        TestCase[] testCases = new TestCase[] {
            // 1. Short String
            new TestCase(
                "Short test (Found)", 
                "ABABDABACDABABCABAB", // Length 19
                "ABABCABAB",           // Length 9
                9 // Expected index: T[9]='A'
            ),
            // 2. Medium-length String
            new TestCase(
                "Medium test (Not found)", 
                "GEEKSFORGEEKSANDALGORITHMS", // Length 26
                "KMP_ALGO",                   // Length 8
                -1 // Pattern not found.
            ),
            // 3. Longer String - demonstrates efficiency on repeating prefixes
            new TestCase(
                "Long test (Repeating prefix)", 
                "AAAAABAAABA", // Length 11
                "AAAA",        // Length 4
                0 // Expected index
            )
        };

        // Running tests
        for (int i = 0; i < testCases.length; i++) {
            TestCase tc = testCases[i];
            System.out.printf("\n%d. Test: %s\n", i + 1, tc.name);
            System.out.println("  Input data:");
            System.out.println("    Text (T):    \"" + tc.text + "\" (Length: " + tc.text.length() + ")");
            System.out.println("    Pattern (P):   \"" + tc.pattern + "\" (Length: " + tc.pattern.length() + ")");
            
            // Call the algorithm and measure time
            long startTime = System.nanoTime();
            int actualIndex = KMP.search(tc.text, tc.pattern);
            long endTime = System.nanoTime();
            
            // Output and comparison
            System.out.println("  Output data:");
            System.out.println("    Expected result: " + (tc.expectedIndex != -1 ? "Index " + tc.expectedIndex : "Not found"));
            System.out.println("    Actual result: " + (actualIndex != -1 ? "Index " + actualIndex : "Not found"));
            System.out.printf("    Execution time: %.3f ms\n", (endTime - startTime) / 1_000_000.0);
            
            // Check
            if (actualIndex == tc.expectedIndex) {
                System.out.println("  Status:  SUCCESS");
            } else {
                System.out.println("  Status:  FAILURE (Expected " + tc.expectedIndex + ", Got " + actualIndex + ")");
            }
        }
    }
}