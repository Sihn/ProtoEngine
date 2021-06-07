package island;

import java.util.ArrayList;

public enum Meteo {
	None, Rain, Snow, Thunder, Storm;

	public static final Meteo getRandomMeteo(boolean cold, boolean hot) {
		if ((int) (Math.random() * 3) > 0)
			return Meteo.None;
		ArrayList<Meteo> meteos = new ArrayList<>();
		meteos.add(Meteo.Thunder);
		meteos.add(Meteo.Storm);
		if (!hot) {
			meteos.add(Meteo.Rain);
		}
		if (cold) {
			meteos.add(Meteo.Snow);
		}
		return meteos.get((int) (Math.random() * meteos.size()));
	}
}
