package core.tools;

import java.awt.Color;

public class RandomColor {
	public static Color getColor() {
		return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	}

	public static Color getSaturatedColor() {
		return Color.getHSBColor((float) Math.random(), 1, 1);
	}

	public static Color getColorFromHue(float hue) {
		return Color.getHSBColor(hue, (float) Math.random(), (float) Math.random());
	}

	public static Color getColorFromColor(Color color) {
		return getColorFromColor(color, 5);
	}

	public static Color getColorFromColor(Color color, int power) {
		boolean light = (int) (Math.random() * 2) == 0;
		int loops = (int) (Math.random() * power);
		for (int i = 0; i < loops; i++) {
			if (light)
				color.brighter();
			else
				color.darker();
		}
		return color;
	}

	public static Color getRed() {
		return new Color(255, (int) (Math.random() * 256), 0);
	}

	public static Color getYellow() {
		return new Color(255, 255, (int) (Math.random() * 256));
	}

	public static Color getGreen() {
		return Color.getHSBColor(.4F, (float) Math.random(), (float) Math.random());
	}

	public static Color getBlue() {
		return new Color(0, (int) (Math.random() * 256), 255);
	}

	public static Color getPurple() {
		int c = (int) (Math.random() * 256);
		return new Color(c, 0, c);
	}

	public static Color getGrey() {
		int c = (int) (Math.random() * 256);
		return new Color(c, c, c);
	}
}
