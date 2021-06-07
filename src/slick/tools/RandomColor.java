package slick.tools;

import org.newdawn.slick.Color;

public class RandomColor {
	public static Color awtToSlick(java.awt.Color c) {
		return new Color(c.getRGB());
	}

	public static Color getHSBColor(float h, float s, float b) {
		return awtToSlick(java.awt.Color.getHSBColor(h, s, b));
	}

	public static Color getColor() {
		return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	}

	public static Color getSaturatedColor() {
		return awtToSlick(java.awt.Color.getHSBColor((float) Math.random(), 1, 1));
	}

	public static Color getColorFromHue(float hue) {
		return awtToSlick(java.awt.Color.getHSBColor(hue, (float) Math.random(), (float) Math.random()));
	}

	public static Color getRed() {
		return new Color(255, (int) (Math.random() * 256), 0);
	}

	public static Color getYellow() {
		return new Color(255, 255, (int) (Math.random() * 256));
	}

	public static Color getGreen() {
		return awtToSlick(java.awt.Color.getHSBColor(.4F, (float) Math.random(), (float) Math.random()));
	}

	public static Color getBlue() {
		return new Color(0, (int) (Math.random() * 256), 255);
	}

	public static Color getPurple() {
		int c = (int) (Math.random() * 256);
		return new Color(c, 0, c);
	}
}
