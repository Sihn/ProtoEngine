package core;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface Drawable {
	public int getZ();

	public boolean isFixed();

	public Rectangle getRect();

	public boolean isDead();

	public void render(Graphics2D g, Screen screen);
}
