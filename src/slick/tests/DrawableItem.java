package slick.tests;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class DrawableItem {
	protected Body body;
	private int width;
	private int height;

	public DrawableItem(Body body, int width, int height) {
		this.body = body;
		this.width = width;
		this.height = height;
	}

	public float getX() {
		return body.getPosition().x;
	}

	public float getY() {
		return body.getPosition().y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getAngle() {
		return body.getAngle();
	}

	public abstract void update(GameContainer gc, int delta) throws SlickException;

	public abstract void render(GameContainer gc, Graphics g) throws SlickException;
}
