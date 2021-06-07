package island;

/*
 Continent
 String name;
 Region[][] regions = new Region[32][32];

 Region
 String name;
 Island[][] islands = new Island[8][8];
 Temperature
 Normal / Cold / Hot

 Island
 String name
 Wizard owner
 Meteo
 None / Rain (-H) / Snow (+C) / Thunder / Storm
 Size
 1~6
 Features
 Plain
 Hill
 Mountain (2)
 Desert (+H)
 Oasis (+H)
 Forest (-H)
 Jungle (-C)
 Volcano (2-C)

 City
 Normal / Rich (2) / Futuristic (3)
 Mine
 Little / Medium / Large
 Den (3, exclusive)

 Wizard
 String name;
 Color aura;
 */

public class Island {
	public static final int MIN_SIZE = 1;
	public static final int MAX_SIZE = 6;

	public final Region region;
	public final String name;
	public Wizard owner = null;
	public Meteo meteo;
	public final int size;
	public final Feature[] features;

	public Island(Region region, String name) {
		this.region = region;
		this.name = name;
		meteo = Meteo.getRandomMeteo(region.isCold(), region.isHot());
		size = (int) (Math.random() * (MAX_SIZE - MIN_SIZE + 1)) + MIN_SIZE;
		features = new Feature[size];
		initializeFeatures();
	}

	private void initializeFeatures() {
		int remainingPlaces = size;
		while (remainingPlaces > 0) {
			Feature feature = Feature.getRandomFeature();
			if (feature.getSize() > remainingPlaces) {
				continue;
			}
			if (feature.hasRequirment(region.isCold(), region.isHot(), size)) {
				for (int i = 0; i < feature.getSize(); i++) {
					features[size - remainingPlaces] = feature;
					remainingPlaces--;
				}
			}
		}
	}
}
