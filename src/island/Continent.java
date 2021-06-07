package island;

import core.Log;

public class Continent {
	public static final int DEFAULT_SIZE = 8;

	public final String name;
	public final Region[][] regions;

	public Continent(String name, int size) {
		this.name = name == null || name.isEmpty() ? "???" : name;
		regions = new Region[size][size];
		makeAllRegions();
	}

	public void makeAllRegions() {
		for (int y = 0; y < regions.length; y++) {
			for (int x = 0; x < regions[y].length; x++) {
				String name = Name.getName(x, y);
				regions[x][y] = new Region(this, name, Region.DEFAULT_SIZE);
			}
		}
	}

	public static void main(String[] args) {
		Continent c = new Continent("Elysium", DEFAULT_SIZE);
		Log.debug("***********************************************************************");
		Log.debug("**** Continent \"" + c.name + "\"");
		Log.debug("***********************************************************************");
		for (Region[] regions : c.regions) {
			for (Region region : regions) {
				Log.debug("");
				Log.debug("=======================================================================");
				Log.debug("==== Région \"" + region.name + "\"");
				Log.debug("=======================================================================");
				Log.debug("Température : " + region.temperature);
				for (Island[] islands : region.islands) {
					for (Island island : islands) {
						Log.debug("");
						Log.debug("-----------------------------------------------------------------------");
						Log.debug("---- Île \"" + island.name + "\"");
						Log.debug("-----------------------------------------------------------------------");
						Log.debug("Taille : " + island.size);
						Log.debug("Composition :");
						for (Feature feature : island.features)
							Log.debug("\t- " + feature.getName() + "\t (" + feature.hashCode() + ")");
					}
				}
			}
		}
	}
}
