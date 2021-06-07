package sprites.particles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import core.Screen;
import core.tools.Direction;
import core.tools.RandomColor;

public class ParticleSquare extends SpriteParticle {
	int sizeMax;
	int size;
	int angle;
	float speedX;
	float speedY;
	float maxDuration;
	int rotateSpeed;
	Color color;

	public ParticleSquare(float x, float y, int duration, int size, Direction dir) {
		this.x = x;
		this.y = y;
		this.duration = duration;
		this.sizeMax = size;
		angle = rand(359);
		switch (dir) {
		case UP:
			speedX = 0;
			speedY = -2;
			break;
		case DOWN:
			speedX = 0;
			speedY = 2;
			break;
		case LEFT:
			speedX = -2;
			speedY = 0;
			break;
		case RIGHT:
			speedX = 2;
			speedY = 0;
			break;
		case UP_LEFT:
			speedX = -1;
			speedY = -1;
			break;
		case UP_RIGHT:
			speedX = 1;
			speedY = -1;
			break;
		case DOWN_LEFT:
			speedX = -1;
			speedY = 1;
			break;
		case DOWN_RIGHT:
			speedX = 1;
			speedY = 1;
			break;
		case CENTER:
			speedX = 0;
			speedY = 0;
			break;
		default:
			speedX = rand(4) - 2;
			speedY = rand(4) - 2;
		}
		maxDuration = this.duration;
		rotateSpeed = rand(358) + 1;
	}

	@Override
	public void update() {
		duration--;
		x += speedX;
		y += speedY;
		while (angle >= 360)
			angle -= 360;
		angle += rotateSpeed;
		color = RandomColor.getBlue();
		size = rand(sizeMax);
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
		g.fillRect(dx, dy, size, size);
	}
}
