package island.features;

import island.Feature;

public class Oasis extends Feature {
	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return hot;
	}
}
