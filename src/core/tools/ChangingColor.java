package core.tools;

import java.awt.Color;

public class ChangingColor {
	private Color targetColor;
	private Color currentColor;
	private int colorDuration;

	public ChangingColor(int rgb) {
		this(new Color(rgb));
	}

	public ChangingColor(Color startColor) {
		targetColor = startColor;
		currentColor = startColor;
	}

	public void setColor(Color color) {
		setColor(color, 0);
	}

	public void setColor(Color color, int duration) {
		targetColor = color;
		colorDuration = duration;
		if (duration == 0)
			currentColor = color;
	}

	public Color getColor() {
		return currentColor;
	}

	public void update() {
		if (currentColor != targetColor) {
			if (colorDuration == 0) {
				currentColor = targetColor;
			} else {
				int r1 = currentColor.getRed();
				int g1 = currentColor.getGreen();
				int b1 = currentColor.getBlue();
				int a1 = currentColor.getAlpha();
				int r2 = targetColor.getRed();
				int g2 = targetColor.getGreen();
				int b2 = targetColor.getBlue();
				int a2 = targetColor.getAlpha();
				r1 -= (r1 - r2) / colorDuration;
				g1 -= (g1 - g2) / colorDuration;
				b1 -= (b1 - b2) / colorDuration;
				a1 -= (a1 - a2) / colorDuration;
				currentColor = new Color(r1, g1, b1, a1);
				colorDuration--;
			}
		}
	}
}
