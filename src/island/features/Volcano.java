package island.features;

import island.Feature;

public class Volcano extends Feature {
	public static final int REQUIRED_SIZE = Mountain.REQUIRED_SIZE;

	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return !cold && size >= REQUIRED_SIZE;
	}

	@Override
	public int getSize() {
		return REQUIRED_SIZE;
	}
}
