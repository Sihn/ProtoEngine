package sprites.particles;

import java.awt.Color;
import java.awt.Graphics2D;

import core.Screen;
import core.tools.RandomColor;

public class ParticleStar extends SpriteParticle {
	public static enum StarColor {
		RANDOM, RED, YELLOW, GREEN, BLUE, GREY
	}

	int size;
	float spread;
	StarColor colorType;
	Color color;

	public ParticleStar(float x, float y, int duration, int size, float spread, StarColor colorType) {
		this.x = x;
		this.y = y;
		this.duration = rand(duration) + duration;
		this.size = rand(size);
		this.spread = spread;
		this.colorType = colorType;
	}

	@Override
	public void update() {
		duration--;
		x += Math.random() * spread - spread / 2;
		y += Math.random() * spread - spread / 2;
		switch (colorType) {
		case RANDOM:
			color = RandomColor.getSaturatedColor();
			break;
		case RED:
			color = RandomColor.getRed();
			break;
		case YELLOW:
			color = RandomColor.getYellow();
			break;
		case GREEN:
			color = RandomColor.getGreen();
			break;
		case BLUE:
			color = RandomColor.getBlue();
			break;
		case GREY:
			color = RandomColor.getGrey();
			break;
		}
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		int dx = size / -2;
		int dy = size / -2;
		double tx = x - size / 2 - dx;
		double ty = y - size / 2 - dy;
		int s2 = size / 2;
		int s4 = size / 4;
		g.setColor(color);
		g.translate(tx, ty);
		g.drawLine(dx, dy + s2, dx + size, dy + s2);
		g.drawLine(dx + s2, dy, dx + s2, dy + size);
		g.drawLine(dx + s4, dy + s4, dx + size - s4, dy + size - s4);
		g.drawLine(dx + s4, dy + size - s4, dx + size - s4, dy + s4);
		if (rand(3) == 0)
			g.drawOval(dx + s4, dy + s4, size - s4 * 2, size - s4 * 2);
	}
}
