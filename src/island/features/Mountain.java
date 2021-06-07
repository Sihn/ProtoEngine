package island.features;

import island.Feature;

public class Mountain extends Feature {
	public static final int REQUIRED_SIZE = 3;

	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return size >= REQUIRED_SIZE;
	}

	@Override
	public int getSize() {
		return REQUIRED_SIZE;
	}
}
