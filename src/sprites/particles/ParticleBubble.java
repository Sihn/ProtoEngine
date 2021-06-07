package sprites.particles;

import java.awt.Color;
import java.awt.Graphics2D;

import core.Screen;
import core.tools.RandomColor;

public class ParticleBubble extends SpriteParticle {
	int size;
	float spread;
	Color color;
	int currentSize;

	public ParticleBubble(float x, float y, int duration, int size, float spread) {
		this.x = x;
		this.y = y;
		this.duration = rand(duration) + duration;
		this.size = size;
		this.spread = spread;
	}

	@Override
	public void update() {
		duration--;
		x += Math.random() * spread - spread / 2;
		y -= Math.random() * spread;
		currentSize = rand(this.size);
		color = RandomColor.getBlue();
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		int dx = size / -2;
		int dy = size / -2;
		double tx = x - size / 2 - dx;
		double ty = y - size / 2 - dy;
		g.translate(tx, ty);
		g.setColor(color);
		if (duration > 1) {
			g.drawOval(dx, dy, currentSize, currentSize);
		} else {
			g.drawLine(dx, dy, dx + currentSize, dy + currentSize);
			g.drawLine(dx + currentSize, dy, dx, dy + currentSize);
		}
	}
}
