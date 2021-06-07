package slick.maze;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class GameLogic implements Logic {
	@Override
	public void init(GameContainer gc, ArrayList<DrawableItem> items) throws SlickException {
		items.add(new Maze());
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {}
}
