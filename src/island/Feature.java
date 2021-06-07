package island;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public abstract class Feature {
	public final static String[] featuresNames = { "City", "Den", "Desert", "Forest", "Hill", "Jungle", "Mine", "Mountain", "Oasis", "Plain", "Savanna", "Volcano" };
	private static ArrayList<Constructor<? extends Feature>> constructors = null;

	private static final void makeFeaturesConstructors() {
		constructors = new ArrayList<>();
		for (String featureName : featuresNames) {
			try {
				Class<? extends Feature> featureClass = Class.forName("island.features." + featureName).asSubclass(Feature.class);
				constructors.add(featureClass.getConstructor(featureClass.getClasses()));
			} catch (ClassNotFoundException e) {
				System.err.println("Classe \"" + featureName + "\" introuvable.");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static final Feature getRandomFeature() {
		if (constructors == null)
			makeFeaturesConstructors();
		Constructor<? extends Feature> constructor = constructors.get((int) (Math.random() * constructors.size()));
		try {
			return constructor.newInstance(new Object[0]);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public abstract boolean hasRequirment(boolean cold, boolean hot, int size);

	public int getSize() {
		return 1;
	}
}
