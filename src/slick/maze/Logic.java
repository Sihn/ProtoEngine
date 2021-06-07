package slick.maze;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public interface Logic {
	public void init(GameContainer gc, ArrayList<DrawableItem> items) throws SlickException;

	public abstract void update(GameContainer gc, int delta) throws SlickException;
}
