package island.features;

import island.Feature;

public class Jungle extends Feature {
	@Override
	public boolean hasRequirment(boolean cold, boolean hot, int size) {
		return !cold;
	}
}
