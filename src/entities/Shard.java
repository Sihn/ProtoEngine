package entities;

import level.Level;
import core.Main;
import core.ParticlesEngine;
import core.Screen;

public class Shard extends Entity {
	private int step = 0;

	public Shard(int x, int y, Level level) {
		super("shard.png", 32, 32, x, y, 12, 30, 4, 1, level);
		hue = (float) Math.random();
	}

	@Override
	protected void update(Main main, Screen screen) {
		step++;
		if (step % (Main.fps / 10) == 0) {
			column++;
			while (column >= maxColumns)
				column -= maxColumns;
		}
	}

	@Override
	public void collisionWithEntity(Entity entity) {
		if (entity instanceof Player) {
			((Player) entity).addItems(1);
			ParticlesEngine.shardsBlast(getX(), getY() - 16, 100, 32, 6, hue, 10, false);
			dispose();
		}
	}
}
