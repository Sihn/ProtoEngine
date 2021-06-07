package island.features;

import island.Feature;

public class Mine extends Feature {
	public static final int MIN_MINESIZE = 1;
	public static final int MAX_MINESIZE = 3;

	public int minesize;

	public Mine() {
		minesize = (int) (Math.random() * (MAX_MINESIZE - MIN_MINESIZE + 1)) + MIN_MINESIZE;
	}

	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return true;
	}
}
