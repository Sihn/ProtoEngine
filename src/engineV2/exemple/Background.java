package engineV2.exemple;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;

import engineV2.Drawable;
import engineV2.Screen;

public class Background implements Drawable {
	private Color[] colors;
	private Paint gradient;
	private int size;

	public Background(Screen screen, Color... colors) {
		this.colors = colors;
		size = colors.length;
		if (size > 1) {
			float[] positions = new float[size];
			for (int i = 0; i < size; i++)
				positions[i] = 1F / (size - 1) * i;
			gradient = new LinearGradientPaint(0, 0, 0, screen.getHeight(), positions, colors);
		}
	}

	@Override
	public int getZ() {
		return 0;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		if (size >= 1) {
			if (size > 1)
				g.setPaint(gradient);
			else
				g.setColor(colors[0]);
			g.fillRect(0, 0, screen.ingameWidth, screen.ingameHeight);
		}
	}
}
