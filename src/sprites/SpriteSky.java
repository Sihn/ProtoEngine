package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;

import level.Sky;
import core.Screen;
import core.tools.ZIndex;

public class SpriteSky extends Sprite {
	private Sky datas;

	public SpriteSky(Sky datas) {
		super(ZIndex.background, true);
		this.datas = datas;
		this.setFixed(true);
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		int size = datas.getColors().size();
		if (size >= 1) {
			if (size < 2) {
				g.setColor(datas.getColors().get(0).getColor());
			} else {
				Color[] colors = new Color[size];
				float[] positions = new float[size];
				for (int i = 0; i < size; i++) {
					colors[i] = datas.getColors().get(i).getColor();
					positions[i] = 1F / (size - 1) * i;
				}
				g.setPaint(new LinearGradientPaint(0, 0, 0, screen.getGameHeight(), positions, colors));
			}
			g.fillRect(0, 0, screen.getGameWidth(), screen.getGameHeight());
		}
	}

	@Override
	public Rectangle getRect() {
		return null;
	}
}
