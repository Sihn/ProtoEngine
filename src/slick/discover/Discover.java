package slick.discover;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Discover extends BasicGame {

	public Discover(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer container) throws SlickException {}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {}

	public static void main(String[] args) {
		int resolution = 720;
		double ratio = 16.0 / 9.0;
		boolean fullscreen = false;
		try {
			int width = (int) (resolution * ratio);
			int height = resolution;
			new AppGameContainer(new Discover("Discover - Conquest"), width, height, fullscreen).start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
