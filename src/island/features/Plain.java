package island.features;

import island.Feature;

public class Plain extends Feature {
	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return true;
	}
}
