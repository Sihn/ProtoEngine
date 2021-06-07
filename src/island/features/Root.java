package island.features;

import island.Feature;

public class Root extends Feature {
	public static final int ROOT_SIZE = 3;
	public static final int CHANCE = 10;

	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return size == ROOT_SIZE && (int) (Math.random() * CHANCE) == 0;
	}

	@Override
	public int getSize() {
		return ROOT_SIZE;
	}
}
