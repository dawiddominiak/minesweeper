package bundle.core.util;

import java.util.Random;

/**
 * Class contains useful utilities connected with randomness.
 */
public class Randomness {
    /**
     * Simple algorithm that samples random numbers (in an amount of 'count')
     * in the range from 'start' to 'end'.
     *
     * The computational and memory complexity is low.
     * See <a href="http://stackoverflow.com/a/29750138">algorithm source</a>.
     *
     * @param start range start.
     * @param end range end.
     * @param count amount of random numbers.
     *
     * @return sorted array with sample random numbers .
     */
    public static int[] sampleRandomNumbersWithoutRepetition(Integer start, int end, int count) {
        Random rng = new Random();

        int[] result = new int[count];
        int cur = 0;
        int remaining = end - start;
        for (int i = start; i <= end && count > 0; i++) {
            double probability = rng.nextDouble();
            if (probability < ((double) count) / (double) remaining) {
                count--;
                result[cur++] = i;
            }
            remaining--;
        }
        return result;
    }
}
