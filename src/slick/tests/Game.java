package slick.tests;

import java.util.ArrayList;

import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public interface Game {
	public void init(GameContainer gc, World world, ArrayList<DrawableItem> items) throws SlickException;

	public void update(GameContainer gc, int delta) throws SlickException;
}
