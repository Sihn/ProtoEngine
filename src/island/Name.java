package island;

import java.util.HashMap;
import java.util.Random;

public final class Name {
	public static final int MIN_WORDS = 2;
	public static final int MAX_WORDS = 3;

	private Name() {};

	private static HashMap<Long, String> cache = new HashMap<>();
	private final static String[] values = { "alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel", "isaac", "juliett", "kilo", "lima", "mike", "november", "oscar", "peter", "queen", "romeo", "sierra", "tango", "urban", "victor", "whiskey", "x-ray", "yankee", "zebra" };

	public static String getName(int... key) {
		long seed = key[0];
		for (int i = 1; i < key.length; i++)
			for (int j = 0; j < key[i - 1]; j++)
				seed += (key[i]);
		if (!cache.containsKey(seed))
			makeName(seed);
		return cache.get(seed);
	}

	private static void makeName(long seed) {
		Random r = new Random(seed);
		int size = r.nextInt(MAX_WORDS - MIN_WORDS + 1) + MIN_WORDS;
		String name = "";
		for (int i = 0; i < size; i++) {
			String word = values[r.nextInt(values.length)];
			char[] chars = word.trim().toLowerCase().toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			name += String.valueOf(chars);
			if (i + 1 < size)
				name += " ";
		}
		cache.put(seed, name);
	}
}
