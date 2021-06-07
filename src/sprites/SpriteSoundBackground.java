package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import core.Screen;
import core.tools.ZIndex;

public class SpriteSoundBackground extends Sprite {
	private int min;
	private int max;

	public SpriteSoundBackground(int min, int max) {
		super(ZIndex.background, true);
		this.min = min;
		this.max = max;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		float nbNotes = max - min + 1F;
		float side = screen.getGameWidth() / nbNotes;
		for (float x = 0; x < screen.getGameWidth() / side; x++) {
			float brightness = x % 2 == 1 ? .2F : .3F;
			g.setColor(Color.getHSBColor(x / nbNotes / 2, 1F, brightness));
			g.fillRect((int) (x * side) + 1, 0, (int) (side + 1), screen.getGameHeight());
		}
		g.setColor(Color.GRAY);
		g.draw3DRect(0, 0, screen.getGameWidth() - 1, screen.getGameHeight() - 1, true);
	}

	@Override
	public Rectangle getRect() {
		return null;
	}
}
