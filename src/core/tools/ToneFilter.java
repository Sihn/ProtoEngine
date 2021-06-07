package core.tools;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class ToneFilter extends RGBImageFilter {
	Color tone;

	public ToneFilter(Color tone) {
		canFilterIndexColorModel = true;
		this.tone = tone;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		return rgb & tone.getRGB();
	}
}
