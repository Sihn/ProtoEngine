package slick.tests;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import slick.tools.RandomColor;


public class Sprite extends DrawableItem {
	private int column = 0;
	private int line = 0;
	private int lastColumn = column;
	private int lastLine = line;
	private Image baseImage;
	private Image currentFrame;
	private int frameWidth;
	private int frameHeight;
	private Color color;

	public Sprite(Body body, int width, int height, String fileName, int MaxColumns, int MaxLines) throws SlickException {
		super(body, width, height);
		baseImage = new Image(fileName);
		frameWidth = baseImage.getWidth() / MaxColumns;
		frameHeight = baseImage.getHeight() / MaxLines;
		setCurrentFrame();
		color = RandomColor.getSaturatedColor();
	}

	public void setCurrentFrame() {
		setFrame(column, line);
	}

	public void setFrame(int column, int line) {
		lastColumn = column;
		lastLine = line;
		currentFrame = baseImage.getSubImage(column * frameWidth, line * frameHeight, frameWidth, frameHeight);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (lastColumn != column || lastLine != line)
			setCurrentFrame();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.rotate(getX(), getY(), (float) Math.toDegrees(getAngle()));
		g.setColor(color);
		g.fillRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
		currentFrame.draw(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
	}
}
