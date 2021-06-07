package sprites.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import core.Screen;
import core.tools.RandomColor;

public class ParticleDeath extends SpriteParticle {
	int size;
	float spread;
	Color baseColor;
	Color color;
	int currentSize;
	float maxDuration;

	public ParticleDeath(float x, float y, int duration, int size, float spread, Color baseColor) {
		this.x = x;
		this.y = y;
		this.duration = rand(duration / 2) + duration / 2;
		this.size = size;
		this.spread = spread;
		this.baseColor = baseColor;
		maxDuration = this.duration;
	}

	@Override
	public void update() {
		duration--;
		x += Math.random() * spread - spread / 2;
		y -= Math.random() * spread - spread / 2;
		currentSize = rand(this.size);
		color = RandomColor.getColorFromColor(baseColor);
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		int dx = size / -2;
		int dy = size / -2;
		double tx = x - size / 2 - dx;
		double ty = y - size / 2 - dy;
		g.translate(tx, ty);
		g.setColor(color);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, duration / maxDuration));
		g.fillOval(dx, dy, currentSize, currentSize);
	}
}
