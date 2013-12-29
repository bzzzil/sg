package util;

import world.Galaxy;

public class StarName {
	protected static String[] syllables = new String [] {
			"a", "ad", "an", "az", 
			"ba", "be", "bi", "bu",
			"ca", "ce", "ci", "che",
			"da", "de",
			"eg",
			"fa", "fe", "fu",
			"ga", "go",
			"hi", "he", "ho",
			"id",
			"jo", "ja",
			"ka", "ke", "ko", "ku",
			"la", "lo", "lu",
			"ma", "mi", "mu",
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

	public static String getName()
	{
		String name = new String();
		
		int syllablesCount = Galaxy.getInstance().generator.nextInt(2)+2;
		
		for (int i=0;i<syllablesCount;i++)
		{
			int syllableId = Galaxy.getInstance().generator.nextInt(syllables.length);
			
			name+=syllables[syllableId];
		}
		
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}
	
}
