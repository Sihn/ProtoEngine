package core.tools;

import java.awt.image.RGBImageFilter;

public class BackgroundFilter extends RGBImageFilter {
	int alpha;

	public BackgroundFilter(int alpha) {
		canFilterIndexColorModel = true;
		this.alpha = alpha | 0xFF000000;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		return (rgb | 0xFF000000) == alpha ? 0 : rgb;
	}
}
