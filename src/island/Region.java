package island;

public class Region {
	public static final int DEFAULT_SIZE = 4;
	public static final int MIN_TEMP = -20;
	public static final int MAX_TEMP = 40;
	public static final int COLD = 0;
	public static final int HOT = 20;

	public final Continent continent;
	public final String name;
	public final Island[][] islands;
	public final int temperature;

	public Region(Continent continent, String name, int size) {
		this.continent = continent;
		this.name = name == null || name.isEmpty() ? "???" : name;
		islands = new Island[size][size];
		temperature = (int) (Math.random() * (MAX_TEMP - MIN_TEMP + 1)) + MIN_TEMP;
		makeAllIslands();
	}

	public void makeAllIslands() {
		for (int y = 0; y < islands.length; y++) {
			for (int x = 0; x < islands[y].length; x++) {
				String name = this.name + " : " + x + "-" + y;
				islands[x][y] = new Island(this, name);
			}
		}
	}

	public boolean isCold() {
		return temperature < COLD;
	}

	public boolean isHot() {
		return temperature > HOT;
	}

	public boolean isNormal() {
		return !isHot() && !isCold();
	}
}
