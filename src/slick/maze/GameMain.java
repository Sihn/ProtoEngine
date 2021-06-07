package slick.maze;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class GameMain extends BasicGame {
	public static boolean Debug = false;
	public static final int FPS = 60;
	public static float Zoom = 1;
	private ArrayList<DrawableItem> items = new ArrayList<>();;
	private Logic logic;

	public GameMain(Logic logic) {
		super("Maze");
		this.logic = logic;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		logic.init(gc, items);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		logic.update(gc, delta);
		for (DrawableItem item : items)
			item.update(gc, delta);
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE))
			gc.exit();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (DrawableItem item : items) {
			g.resetTransform();
			item.render(gc, g);
		}
	}

	public static void main(String[] args) throws SlickException {
		int size = 1080;
		GameMain.Debug = true;
		new AppGameContainer(new GameMain(new GameLogic()), size, size * 3 / 4, false).start();
	}
}
