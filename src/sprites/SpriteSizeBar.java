package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import loops.ParticlesLoop;
import core.Screen;
import core.tools.ZIndex;

public class SpriteSizeBar extends Sprite {
	private ParticlesLoop datas;
	private int x;
	private int y;
	private int width;
	private int height;

	public SpriteSizeBar(ParticlesLoop datas) {
		super(ZIndex.HUD, true);
		this.datas = datas;
		x = 10;
		y = 10;
		width = 50;
		height = 10;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		g.setColor(Color.getHSBColor((float) Math.random(), 1F, 1F));
		g.fillRect(x, y, (int) (datas.getSizeRatio() * width), height);
		g.setColor(Color.GRAY);
		g.draw3DRect(x, y, width, height, true);
	}

	@Override
	public Rectangle getRect() {
		return null;
	}
}
