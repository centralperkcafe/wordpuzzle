package puzzle;

import java.util.Random;

public class LetterGenerator {
	private static final char[] letters = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	private static final double[] frequencies = {
		8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0, 0.15, 0.77, 4.0, 2.4,
		6.7, 7.5, 1.9, 0.095, 6.0, 6.3, 9.1, 2.8, 0.98, 2.4, 0.15, 2.0, 0.074
	};
	private static final double[] cumulativeFrequencies = new double[frequencies.length];
	private static final Random random = new Random();

	static {
		double total = 0;
		for (int i = 0; i < frequencies.length; i++) {
			total += frequencies[i];
			cumulativeFrequencies[i] = total;
		}
	}

	public static char generateLetter() {
		double rand = random.nextDouble() * cumulativeFrequencies[cumulativeFrequencies.length - 1];
		for (int i = 0; i < cumulativeFrequencies.length; i++) {
			if (rand < cumulativeFrequencies[i]) {
				return letters[i];
			}
		}
		// Fallback, should never happen
		return 'a';
	}

	// public static void main(String[] args) {
	// 	// Generate 10 random letters as a sample
	// 	for (int i = 0; i < 10; i++) {
	// 		System.out.println(generateLetter());
	// 	}
	// }
}
