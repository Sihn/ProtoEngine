package sprites.particles;

import java.awt.Rectangle;

import sprites.Sprite;
import core.tools.ZIndex;

public abstract class SpriteParticle extends Sprite {
	protected float x;
	protected float y;
	protected int duration;

	public SpriteParticle() {
		super(ZIndex.inFrontOfEntities);
	}

	public SpriteParticle(int z) {
		super(z, true);
	}

	public int rand(int max) {
		return (int) (Math.random() * (max + 1));
	}

	public abstract void update();

	@Override
	public boolean isDead() {
		return duration <= 0;
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle((int) x, (int) y);
	}
}
