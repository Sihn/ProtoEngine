package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import core.Screen;
import core.Drawable;
import core.tools.ZIndex;

public abstract class Sprite implements Drawable {
	public Image image = null;
	private int z;
	private boolean fixed = false;

	public Sprite() {
		this(ZIndex.background);
	}

	public Sprite(int z) {
		this(z, false);
	}

	public Sprite(int z, boolean fixed) {
		this.z = z;
		this.fixed = fixed;
	}

	@Override
	public int getZ() {
		return z;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public boolean isFixed() {
		return fixed;
	}

	public void drawText(Graphics2D g, int x, int y, String text) {
		drawText(g, x, y, text, Screen.FONT_COLOR, false);
	}

	public void drawText(Graphics2D g, int x, int y, String text, Color color) {
		drawText(g, x, y, text, color, false);
	}

	public void drawText(Graphics2D g, int x, int y, String text, Color color, boolean border) {
		y += g.getFontMetrics().getHeight() / 2;
		if (border) {
			g.setColor(Color.BLACK);
			for (int i = -1; i <= 1; i++)
				for (int j = -1; j <= 1; j++)
					g.drawString(text, x + i, y + j);
		}
		if (color != null)
			g.setColor(color);
		g.drawString(text, x, y);
	}

	@Override
	public boolean isDead() {
		return false;
	}
}
