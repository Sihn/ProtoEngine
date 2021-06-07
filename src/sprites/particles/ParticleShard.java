package sprites.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import core.Screen;
import core.tools.RandomColor;

public class ParticleShard extends SpriteParticle {
	int size;
	int angle;
	float speedX;
	float speedY;
	float maxDuration;
	float hue;
	Color color;
	Polygon shard;

	public ParticleShard(float x, float y, int duration, int size, float spread, float hue) {
		shard = new Polygon();
		shard.addPoint(0, 0);
		for (int i = 0; i < 2; i++)
			shard.addPoint(rand(size - 1), rand(size - 1));
		this.x = x;
		this.y = y;
		this.duration = duration;
		this.size = rand(size);
		angle = rand(359);
		speedX = (float) (Math.random() * spread - spread / 2);
		speedY = (float) (Math.random() * spread - spread / 2);
		maxDuration = this.duration;
		this.hue = hue;
	}

	@Override
	public void update() {
		duration--;
		x += speedX;
		y += speedY;
		angle++;
		while (angle >= 360)
			angle -= 360;
		color = RandomColor.getColorFromHue(hue);
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		int dx = size / -2;
		int dy = size / -2;
		double tx = x - size / 2 - dx;
		double ty = y - size / 2 - dy;
		double theta = Math.toRadians(angle);
		g.translate(tx, ty);
		g.rotate(theta);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, duration / maxDuration));
		g.setColor(color);
		g.fillPolygon(shard);
	}
}
