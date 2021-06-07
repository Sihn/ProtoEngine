package slick.tests;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import slick.tests.games.Loop;

public class Main extends BasicGame {
	public static boolean Debug = false;
	private Game game;
	private World world;
	private ArrayList<DrawableItem> items;

	public Main(Game game) {
		super("CrossEngine");
		this.game = game;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		world = new World(new Vec2(), true);
		items = new ArrayList<>();
		game.init(gc, world, items);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		game.update(gc, delta);
		for (DrawableItem item : items)
			item.update(gc, delta);
		world.step(1F / 60F, 8, 3);
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
		for (String arg : args) {
			switch (arg.toUpperCase()) {
			case "DEBUG":
				Main.Debug = true;
				break;
			case "WORK":
				size = 400;
				break;
			}
		}
		new AppGameContainer(new Main(new Loop()), size, size * 3 / 4, false).start();
	}
}
