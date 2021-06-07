package sprites.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;

import core.Cache;
import core.Screen;
import core.tools.ColorFilter;
import core.tools.ToneFilter;

public class ParticleImage extends SpriteParticle {
	private int angle;
	private float spread;
	private float speedX;
	private float speedY;
	private float maxDuration;

	public ParticleImage(String name, float x, float y, int duration, float spread, float speedX, float speedY) {
		this(name, x, y, 110, duration, spread, speedX, speedY, 0);
	}

	public ParticleImage(String name, float x, float y, int z, int duration, float spread, float speedX, float speedY) {
		this(name, x, y, z, duration, spread, speedX, speedY, 0);
	}

	public ParticleImage(String name, float x, float y, int z, int duration, float spread, float speedX, float speedY, Color color) {
		super(z);
		this.x = x;
		this.y = y;
		this.duration = rand(duration) + duration;
		image = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(Cache.getParticle(name).getSource(), new ToneFilter(color)));
		angle = (int) (Math.random() * 360);
		this.spread = spread * 2;
		this.speedX = speedX;
		this.speedY = speedY;
		maxDuration = this.duration;
	}

	public ParticleImage(String name, float x, float y, int z, int duration, float spread, float speedX, float speedY, float hue) {
		super(z);
		this.x = x;
		this.y = y;
		this.duration = rand(duration) + duration;
		image = Cache.getParticle(name);
		if (hue != 0)
			image = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), new ColorFilter(hue)));
		angle = (int) (Math.random() * 360);
		this.spread = spread * 2;
		this.speedX = speedX;
		this.speedY = speedY;
		maxDuration = this.duration;
	}

	@Override
	public void update() {
		duration--;
		x += speedX;
		y += speedY;
		if (spread > 0) {
			x += (int) (Math.random() * spread - spread / 2);
			y += (int) (Math.random() * spread - spread / 2);
		}
		while (angle >= 360)
			angle -= 360;
		angle += 10;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		if (image == null)
			return;
		int width = image.getWidth(null);
		int height = image.getWidth(null);
		int dx = width / -2;
		int dy = height / -2;
		double tx = x - width / 2 - dx;
		double ty = y - height / 2 - dy;
		double theta = Math.toRadians(angle);
		g.translate(tx, ty);
		g.rotate(theta);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, duration / maxDuration));
		g.drawImage(image, dx, dy, dx + width, dy + height, 0, 0, width, height, null);
	}
}
