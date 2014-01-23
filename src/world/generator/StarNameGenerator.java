package world.generator;

import world.Galaxy;

class StarNameGenerator {
	private static String[] syllables = new String [] {
			"a", "ad", "an", "az",
			"ba", "be", "bi", "bu",
			"ca", "ce", "ci", "che",
			"da", "de",
			"eg",
			"fa", "fe", "fu",
			"ga", "go",
			"ha", "hi", "he", "ho",
			"id",
			"jo", "ja",
			"ka", "ke", "ko", "ku",
			"la", "lo", "lu",
			"ma", "me", "mi", "mu", "mo",
			"na", "ne", "no",
			"pi", "pho",
			"qu",
			"re", "ro", "ry",
			"se", "so", "sy", "she",
			"ta", "te", "to", "tu", "the", "thi",
			"vi",
			"wi",
			"xa", "xe", "xy",
			"y",
			"za"
			};

    private static char[] consonants = new char[] {
            'b', 'c', 'd', 'f', 'g', 'h', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'
    };

	public static String getName() {
		int syllablesCount = Galaxy.getGenerator().nextInt(2)+2;
		String name;

		do {
			name = "";
			for (int i=0;i<syllablesCount;i++) {
				int syllableId = Galaxy.getGenerator().nextInt(syllables.length);
				name += syllables[syllableId];
			}
		} while (name.length()<=2);

        // Try to finalize name with consonant if it is not and random
        if (isVowel(name.charAt(name.length()-1)) && Galaxy.getGenerator().nextBoolean()) {
            name += consonants[Galaxy.getGenerator().nextInt(consonants.length)];
        }
		
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

    /**
     * Checks is given letter is vowel
     *
     * @param c letter to check
     * @return bool
     */
    private static boolean isVowel(char c)
    {
        return java.util.Arrays.binarySearch(consonants, c) < 0;
    }
}
