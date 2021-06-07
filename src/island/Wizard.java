package island;

import java.awt.Color;

public class Wizard {
	public final String name;
	public final Color aura;

	public Wizard(String name, Color aura) {
		this.name = name;
		this.aura = aura;
	}

	public void conquer(Island island) {
		island.owner = this;
	}
}
