package entities;

import level.Level;
import core.Main;
import core.ParticlesEngine;
import core.Screen;
import core.tools.Direction;

public class Croacroa extends Entity {
	private final float Speed = 1;
	private final int AnimationSpeed = 10;
	private int step = 0;

	public Croacroa(int x, int y, Level level) {
		super("croacroa.png", 32, 32, x, y, 30, 28, 4, 2, level);
	}

	@Override
	protected void update(Main main, Screen screen) {
		if (lookAt == lookAtRight) {
			if (canPass(Direction.RIGHT))
				x += Speed;
			else
				lookAt = lookAtLeft;
		} else {
			if (canPass(Direction.LEFT))
				x -= Speed;
			else
				lookAt = lookAtRight;
		}
		step++;
		if (step % AnimationSpeed == 0) {
			column++;
			while (column >= maxColumns)
				column -= maxColumns;
		}
	}

	@Override
	public void collisionWithEntity(Entity entity) {
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (player.isAttacking()) {
				ParticlesEngine.shardsBlast(x, y, 20, 20, 20, 1F / 250, 20, false);
				dispose();
			} else {
				if (player.y < this.y) {
					ParticlesEngine.shardsBlast(x, y, 20, 20, 20, 1F / 250, 20, false);
					dispose();
					player.bounce();
				} else if (!player.isProtected())
					player.addDamage(1);
			}
		}
	}
}
