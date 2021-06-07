package slick.tests;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import slick.tools.RandomColor;


public class TestBox extends DrawableItem {
	Color color;

	public TestBox(Body body, int width, int height) {
		super(body, width, height);
		color = RandomColor.getSaturatedColor();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.rotate(getX(), getY(), (float) Math.toDegrees(getAngle()));
		g.setColor(color.brighter());
		g.fillRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
		g.setColor(color.darker());
		g.drawRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
	}
}
