package entities;

import level.Level;
import sprites.particles.ParticleStar.StarColor;
import core.Main;
import core.ParticlesEngine;
import core.Screen;

public class Scroll extends Entity {
	private final int Unfound = 0;
	private final int Found = 1;
	private final int Speed = 15;
	private int step = 0;

	public Scroll(int x, int y, Level level) {
		super("scroll.png", 32, 32, x, y, 32, 32, 4, 2, level);
	}

	@Override
	protected void update(Main main, Screen screen) {
		step++;
		if (step % Speed == 0) {
			column++;
			while (column >= maxColumns)
				column -= maxColumns;
		}
	}

	@Override
	public void collisionWithEntity(Entity entity) {
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (line == Unfound) {
				line = Found;
				player.addItems(1);
				ParticlesEngine.addStars(getX(), getY() - 16, 100, player.getItemsFound() * 2 + 2, 6, StarColor.RANDOM, 10, false);
			}
		}
	}
}
