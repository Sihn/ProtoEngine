package island.features;

import island.Feature;

public class Desert extends Feature {
	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return hot;
	}
}
