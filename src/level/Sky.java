package level;

import java.awt.Color;
import java.util.ArrayList;

import core.tools.ChangingColor;

public class Sky {
	private ArrayList<ChangingColor> colors = new ArrayList<>();

	public Sky(int[] colors) {
		for (int color : colors)
			this.colors.add(new ChangingColor(color));
	}

	public Sky(Color... colors) {
		for (Color color : colors)
			this.colors.add(new ChangingColor(color));
	}

	public void setColor(int id, int duration, Color color) {
		colors.get(id).setColor(color, duration);
	}

	public void setColors(int duration, Color... colors) {
		if (colors.length != this.colors.size())
			for (Color color : colors)
				this.colors.add(new ChangingColor(color));
		else
			for (int i = 0; i < colors.length; i++)
				this.colors.get(i).setColor(colors[i], duration);
	}

	public void update() {
		for (ChangingColor color : colors)
			color.update();
	}

	public ArrayList<ChangingColor> getColors() {
		return colors;
	}
}
