package island.features;

import island.Feature;

public class Hill extends Feature {
	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return true;
	}
}
