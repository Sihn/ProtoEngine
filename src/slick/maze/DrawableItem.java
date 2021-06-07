package slick.maze;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface DrawableItem {
	public abstract void update(GameContainer gc, int delta) throws SlickException;

	public abstract void render(GameContainer gc, Graphics g) throws SlickException;
}
