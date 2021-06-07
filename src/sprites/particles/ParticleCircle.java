package sprites.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import core.Screen;
import core.tools.RandomColor;

public class ParticleCircle extends SpriteParticle {
	int size;
	float speedX;
	float speedY;
	float maxDuration;
	Color color;

	public ParticleCircle(float x, float y, int duration, int size, float spread) {
		this.x = x;
		this.y = y;
		this.duration = rand(duration) + duration;
		this.size = rand(size);
		speedX = (float) (Math.random() * spread - spread / 2);
		speedY = (float) (Math.random() * spread - spread / 2);
		maxDuration = this.duration;
		color = RandomColor.getSaturatedColor();
	}

	@Override
	public void update() {
		duration--;
		x += speedX;
		y += speedY;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		int dx = size / -2;
		int dy = size / -2;
		double tx = x - size / 2 - dx;
		double ty = y - size / 2 - dy;
		g.translate(tx, ty);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, duration / maxDuration));
		g.setColor(color);
		g.fillOval(dx, dy, size, size);
	}
}
