package island.features;

import island.Feature;

public class City extends Feature {
	public static final int MIN_SIZE = 1;
	public static final int MAX_SIZE = 3;

	private final int size;

	public City() {
		size = (int) (Math.random() * (MAX_SIZE - MIN_SIZE + 1)) + MIN_SIZE;
	}

	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return size >= this.size;
	}

	@Override
	public int getSize() {
		return size;
	}
}
