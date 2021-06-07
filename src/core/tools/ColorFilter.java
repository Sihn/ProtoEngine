package core.tools;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class ColorFilter extends RGBImageFilter {
	float hue;

	public ColorFilter(float hue) {
		canFilterIndexColorModel = true;
		this.hue = hue;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		int g = (rgb >> 8) & 0xff;
		int b = (rgb >> 0) & 0xff;
		if (g != 1 || b != 1)
			return rgb;
		int a = (rgb >> 24) & 0xff;
		int r = (rgb >> 16) & 0xff;
		return Color.getHSBColor(hue, 1, r / 255F).getRGB() & 0x00FFFFFF + a * 0x01000000;
	}
}
